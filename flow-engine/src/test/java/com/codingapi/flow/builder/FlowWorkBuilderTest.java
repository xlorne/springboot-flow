package com.codingapi.flow.builder;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.form.Leave;
import com.codingapi.flow.matcher.AnyOperatorMatcher;
import com.codingapi.flow.matcher.IOperatorMatcher;
import com.codingapi.flow.matcher.SpecifyOperatorMatcher;
import com.codingapi.flow.context.FlowRepositoryContext;
import com.codingapi.flow.repository.*;
import com.codingapi.flow.trigger.IOutTrigger;
import com.codingapi.flow.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowWorkBuilderTest {

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
    void builder() {
        User admin = new User(1, "admin");
        flowOperatorRepository.addOperator(admin);

        User user = new User(2, "user");
        flowOperatorRepository.addOperator(user);

        User depart = new User(3, "depart");
        flowOperatorRepository.addOperator(depart);

        User boss = new User(4, "boss");
        flowOperatorRepository.addOperator(boss);

        IOperatorMatcher anyOperatorMatcher = new AnyOperatorMatcher();
        IOperatorMatcher departOperatorMatcher = new SpecifyOperatorMatcher(depart.getId());
        IOperatorMatcher bossOperatorMatcher = new SpecifyOperatorMatcher(boss.getId());

        IOutTrigger userOutTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                return record.getNode().getNextNodeByCode("depart");
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
                .addNode(FlowNodeFactory.startNode("发起请假", admin, anyOperatorMatcher, userOutTrigger))
                .addNode(FlowNodeFactory.node("部门经理审批", "depart", FlowType.NOT_SIGN, admin, departOperatorMatcher, departOutTrigger))
                .addNode(FlowNodeFactory.node("总经理审批", "boss", FlowType.NOT_SIGN, admin, bossOperatorMatcher, bossOutTrigger))
                .addNode(FlowNodeFactory.overNode("结束", admin))
                .relations("start", "depart", "boss", "over")
                .relations("start", "boss", "over")
                .build();
        assertNotNull(flowWork);
        assertTrue(flowWork.isEnable());
        assertFalse(flowWork.isLock());

    }


    @Test
    void flow() {

        User admin = new User(1, "admin");
        flowOperatorRepository.addOperator(admin);

        User user = new User(2, "user");
        flowOperatorRepository.addOperator(user);

        User depart = new User(3, "depart");
        flowOperatorRepository.addOperator(depart);

        User boss = new User(4, "boss");
        flowOperatorRepository.addOperator(boss);

        IOperatorMatcher anyOperatorMatcher = new AnyOperatorMatcher();
        IOperatorMatcher departOperatorMatcher = new SpecifyOperatorMatcher(depart.getId());
        IOperatorMatcher bossOperatorMatcher = new SpecifyOperatorMatcher(boss.getId());

        IOutTrigger userOutTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                return record.getNode().getNextNodeByCode("depart");
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
                .addNode(FlowNodeFactory.startNode("发起请假", admin, anyOperatorMatcher, userOutTrigger))
                .addNode(FlowNodeFactory.node("部门经理审批", "depart", FlowType.NOT_SIGN, admin, departOperatorMatcher, departOutTrigger))
                .addNode(FlowNodeFactory.node("总经理审批", "boss", FlowType.NOT_SIGN, admin, bossOperatorMatcher, bossOutTrigger))
                .addNode(FlowNodeFactory.overNode("结束", admin))
                .relations("start", "depart", "boss", "over")
                .relations("start", "boss", "over")
                .build();

        Leave leave = new Leave(1, "desc", user, 1, "2020-01-01", "2020-01-03");

        flowWork.createNode(leave, user);

    }


}
