package com.codingapi.flow.builder;

import com.codingapi.flow.creator.DefaultTitleCreator;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.em.NodeType;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.operator.IOperatorMatcher;
import com.codingapi.flow.trigger.IOutTrigger;

public class FlowNodeFactory {

    public static FlowNode startNode(String name, IFlowOperator createUser, IOperatorMatcher operatorMatcher, IOutTrigger outTrigger) {
        FlowNode flowNode = new FlowNode();
        flowNode.setName(name);
        flowNode.setType(NodeType.START);
        flowNode.setCode(FlowNode.CODE_START);
        flowNode.setView(FlowNode.VIEW_DEFAULT);
        flowNode.setTitleCreator(new DefaultTitleCreator());
        flowNode.setFlowType(FlowType.NOT_SIGN);
        flowNode.setCreateTime(System.currentTimeMillis());
        flowNode.setUpdateTime(System.currentTimeMillis());
        flowNode.setCreateUser(createUser);
        flowNode.setOperatorMatcher(operatorMatcher);
        flowNode.setOutTrigger(outTrigger);
        return flowNode;
    }

    public static FlowNode overNode(String name, IFlowOperator createUser, IOperatorMatcher operatorMatcher, IOutTrigger outTrigger) {
        FlowNode flowNode = new FlowNode();
        flowNode.setName(name);
        flowNode.setType(NodeType.OVER);
        flowNode.setCode(FlowNode.CODE_OVER);
        flowNode.setView(FlowNode.VIEW_DEFAULT);
        flowNode.setTitleCreator(new DefaultTitleCreator());
        flowNode.setFlowType(FlowType.NOT_SIGN);
        flowNode.setCreateTime(System.currentTimeMillis());
        flowNode.setUpdateTime(System.currentTimeMillis());
        flowNode.setCreateUser(createUser);
        flowNode.setOperatorMatcher(operatorMatcher);
        flowNode.setOutTrigger(outTrigger);
        return flowNode;
    }

    public static FlowNode node(String name, String code,FlowType flowType, IFlowOperator createUser, IOperatorMatcher operatorMatcher, IOutTrigger outTrigger) {
        FlowNode flowNode = new FlowNode();
        flowNode.setName(name);
        flowNode.setType(NodeType.APPROVAL);
        flowNode.setCode(code);
        flowNode.setView(FlowNode.VIEW_DEFAULT);
        flowNode.setTitleCreator(new DefaultTitleCreator());
        flowNode.setFlowType(flowType);
        flowNode.setCreateTime(System.currentTimeMillis());
        flowNode.setUpdateTime(System.currentTimeMillis());
        flowNode.setCreateUser(createUser);
        flowNode.setOperatorMatcher(operatorMatcher);
        flowNode.setOutTrigger(outTrigger);
        return flowNode;
    }
}
