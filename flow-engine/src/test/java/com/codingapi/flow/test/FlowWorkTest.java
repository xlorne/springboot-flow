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

    private final UserRepository userRepository = new UserRepository();
    private final FlowRecordRepository flowRecordRepository = new FlowRecordRepositoryImpl();
    private final FlowWorkRepository flowWorkRepository = new FlowWorkRepositoryImpl();
    private final FlowNodeRepository flowNodeRepository = new FlowNodeRepositoryImpl();
    private final BindDataSnapshotRepository bindDataSnapshotRepository = new BindDataSnapshotRepositoryImpl();

    @BeforeEach
    void before() {
        FlowRepositoryContext.getInstance().bind(userRepository);
        FlowRepositoryContext.getInstance().bind(flowRecordRepository);
        FlowRepositoryContext.getInstance().bind(flowWorkRepository);
        FlowRepositoryContext.getInstance().bind(flowNodeRepository);
        FlowRepositoryContext.getInstance().bind(bindDataSnapshotRepository);
    }


    @Test
    void flow() {

        User admin = new User("admin");
        userRepository.save(admin);

        User user = new User("user");
        userRepository.save(user);

        User depart = new User("depart");
        userRepository.save(depart);

        User boss = new User("boss");
        userRepository.save(boss);

        IOperatorMatcher anyOperatorMatcher = new AnyOperatorMatcher();
        IOperatorMatcher departOperatorMatcher = new SpecifyOperatorMatcher(depart.getId());
        IOperatorMatcher bossOperatorMatcher = new SpecifyOperatorMatcher(boss.getId());

        IOutTrigger userOutTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                Leave leave = (Leave) record.getBindData();
                if (leave.getLeaveDays() >= 3) {
                    return record.getNextNodeByCode("boss");
                } else {
                    return record.getNextNodeByCode("depart");
                }
            }
        };

        IOutTrigger departOutTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                return record.getNextNodeByCode("depart");
            }
        };

        IOutTrigger bossOutTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                return record.getNextNodeByCode("over");
            }
        };

        FlowWork flowWork = FlowWorkBuilder
                .Builder(admin)
                .title("请假流程")
                .description("请假流程")
                .nodes()
                .node(FlowNodeFactory.Builder(admin).startNode("发起请假", anyOperatorMatcher, userOutTrigger))
                .node(FlowNodeFactory.Builder(admin).node("部门经理审批", "depart", FlowType.NOT_SIGN, departOutTrigger, departOperatorMatcher))
                .node(FlowNodeFactory.Builder(admin).node("总经理审批", "boss", FlowType.NOT_SIGN, bossOutTrigger, bossOperatorMatcher))
                .node(FlowNodeFactory.Builder(admin).overNode("结束"))
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
