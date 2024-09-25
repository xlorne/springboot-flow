package com.codingapi.flow.builder;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.form.Leave;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.operator.IOperatorMatcher;
import com.codingapi.flow.trigger.IOutTrigger;
import com.codingapi.flow.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowWorkBuilderTest {

    @Test
    void builder() {
        User admin = new User(1, "admin");

        IOperatorMatcher anyUserMatcher = new IOperatorMatcher() {
            @Override
            public boolean matcher(IFlowOperator operator) {
                return true;
            }
        };

        IOutTrigger outTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                return null;
            }
        };

        FlowWork flowWork = FlowWorkBuilder.Builder()
                .title("请假流程")
                .createUser(admin)
                .description("请假流程")
                .nodeBuilder()
                .addNode(FlowNodeFactory.startNode("发起请假", admin, anyUserMatcher, outTrigger))
                .addNode(FlowNodeFactory.node("部门经理审批", "depart", FlowType.NOT_SIGN, admin, anyUserMatcher, outTrigger))
                .addNode(FlowNodeFactory.node("总经理审批", "boss", FlowType.NOT_SIGN, admin, anyUserMatcher, outTrigger))
                .addNode(FlowNodeFactory.overNode("结束", admin, anyUserMatcher, outTrigger))
                .relations("start", "depart", "boss", "over")
                .relations("start", "boss", "over")
                .build();
        assertNotNull(flowWork);
        assertTrue(flowWork.isEnable());
        assertFalse(flowWork.isLock());

    }


    @Test
    void flow(){

        User admin = new User(1, "admin");

        IOperatorMatcher anyUserMatcher = new IOperatorMatcher() {
            @Override
            public boolean matcher(IFlowOperator operator) {
                return true;
            }
        };

        IOutTrigger outTrigger = new IOutTrigger() {
            @Override
            public FlowNode trigger(FlowRecord record) {
                return null;
            }
        };

        FlowWork flowWork = FlowWorkBuilder.Builder()
                .title("请假流程")
                .createUser(admin)
                .description("请假流程")
                .nodeBuilder()
                .addNode(FlowNodeFactory.startNode("发起请假", admin, anyUserMatcher, outTrigger))
                .addNode(FlowNodeFactory.node("部门经理审批", "depart", FlowType.NOT_SIGN, admin, anyUserMatcher, outTrigger))
                .addNode(FlowNodeFactory.node("总经理审批", "boss", FlowType.NOT_SIGN, admin, anyUserMatcher, outTrigger))
                .addNode(FlowNodeFactory.overNode("结束", admin, anyUserMatcher, outTrigger))
                .relations("start", "depart", "boss", "over")
                .relations("start", "boss", "over")
                .build();

        User user = new User(2, "user");
        Leave leave = new Leave(1, "desc", user, 1, "2020-01-01", "2020-01-03");

        flowWork.createNode(leave,user);

    }


}
