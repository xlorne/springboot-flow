package com.codingapi.flow.test;

import com.codingapi.flow.builder.FlowNodeFactory;
import com.codingapi.flow.builder.FlowWorkBuilder;
import com.codingapi.flow.context.FlowRepositoryContext;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.em.FlowStatus;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.em.NodeStatus;
import com.codingapi.flow.form.Leave;
import com.codingapi.flow.matcher.AnyOperatorMatcher;
import com.codingapi.flow.matcher.IOperatorMatcher;
import com.codingapi.flow.matcher.SpecifyOperatorMatcher;
import com.codingapi.flow.repository.*;
import com.codingapi.flow.trigger.IOutTrigger;
import com.codingapi.flow.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlowWorkTest {

    private final FlowOperatorRepositoryImpl flowOperatorRepository = new FlowOperatorRepositoryImpl();
    private final FlowRecordRepository flowRecordRepository = new FlowRecordRepositoryImpl();
    private final FlowWorkRepository flowWorkRepository = new FlowWorkRepositoryImpl();
    private final FlowNodeRepository flowNodeRepository = new FlowNodeRepositoryImpl();
    private final BindDataSnapshotRepository bindDataSnapshotRepository = new BindDataSnapshotRepositoryImpl();

    @BeforeEach
    void before() {
        FlowRepositoryContext.getInstance().bind(flowOperatorRepository);
        FlowRepositoryContext.getInstance().bind(flowRecordRepository);
        FlowRepositoryContext.getInstance().bind(flowWorkRepository);
        FlowRepositoryContext.getInstance().bind(flowNodeRepository);
        FlowRepositoryContext.getInstance().bind(bindDataSnapshotRepository);
    }


    @Test
    void flow() {

        User admin = new User("admin");
        flowOperatorRepository.addOperator(admin);

        User user = new User("user");
        flowOperatorRepository.addOperator(user);

        User depart = new User("depart");
        flowOperatorRepository.addOperator(depart);

        User boss = new User("boss");
        flowOperatorRepository.addOperator(boss);

        IOperatorMatcher anyOperatorMatcher = new AnyOperatorMatcher();
        IOperatorMatcher departOperatorMatcher = new SpecifyOperatorMatcher(depart.getId());
        IOperatorMatcher bossOperatorMatcher = new SpecifyOperatorMatcher(boss.getId());

        IOutTrigger userOutTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                Leave leave = (Leave) record.getBindData();
                if (leave.getLeaveDays() >= 3) {
                    return record.getNode().getNextNodeByCode("boss");
                } else {
                    return record.getNode().getNextNodeByCode("depart");
                }
            }
        };

        IOutTrigger departOutTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                return record.getNode().getNextNodeByCode("depart");
            }
        };

        IOutTrigger bossOutTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                return record.getNode().getNextNodeByCode("over");
            }
        };

        FlowWork flowWork = FlowWorkBuilder.Builder()
                .title("请假流程")
                .createUser(admin)
                .description("请假流程")
                .nodeBuilder()
                .addNode(FlowNodeFactory.builder(admin).startNode("发起请假", anyOperatorMatcher, userOutTrigger))
                .addNode(FlowNodeFactory.builder(admin).node("部门经理审批", "depart", FlowType.NOT_SIGN, departOutTrigger, departOperatorMatcher))
                .addNode(FlowNodeFactory.builder(admin).node("总经理审批", "boss", FlowType.NOT_SIGN, bossOutTrigger, bossOperatorMatcher))
                .addNode(FlowNodeFactory.builder(admin).overNode("结束"))
                .relations()
                .relation("start", "depart", "boss", "over")
                .relation("start", "boss", "over")
                .build();

        // 创建请假数据
        Leave leave = new Leave(1, "desc", user, 1, "2020-01-01", "2020-01-05");

        // 发起请假流程
        flowWork.createNode(leave, user);

        // 老板的待办列表
        List<FlowRecord> bossRecords = flowRecordRepository.findFlowRecordByOperatorId(boss.getId());
        assertEquals(1, bossRecords.size());

        FlowRecord bossRecord = bossRecords.get(0);

        assertEquals(bossRecord.getFlowStatus(), FlowStatus.RUNNING);
        assertEquals(bossRecord.getNodeStatus(), NodeStatus.TODO);

        // 用户的已办列表
        List<FlowRecord> userRecords = flowRecordRepository.findFlowRecordByOperatorId(user.getId());
        assertEquals(1, userRecords.size());

        assertEquals(userRecords.get(0).getNodeStatus(), NodeStatus.DONE);

        // 批准请假
        bossRecord.submit("同意");

        bossRecords = flowRecordRepository.findFlowRecordByOperatorId(boss.getId());
        assertEquals(2, bossRecords.size());

        assertEquals(bossRecord.getNodeStatus(), NodeStatus.DONE);
        assertEquals(bossRecord.getFlowStatus(), FlowStatus.FINISH);

        // 本流程的所有记录
        assertEquals(3, flowRecordRepository.findFlowRecordByProcessId(bossRecord.getProcessId()).size());

    }
}
