package com.codingapi.flow.builder;

import com.codingapi.flow.creator.ITitleCreator;
import com.codingapi.flow.data.IBindData;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.em.NodeType;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.operator.IOperatorMatcher;
import com.codingapi.flow.trigger.IErrTrigger;
import com.codingapi.flow.trigger.IOutTrigger;

import java.util.List;

public class FlowNodeBuilder {

    private final FlowNode flowNode = new FlowNode();

    private FlowNodeBuilder() {
        flowNode.setCreateTime(System.currentTimeMillis());
    }

    public static FlowNodeBuilder Builder() {
        return new FlowNodeBuilder();
    }

    public FlowNode build() {
        flowNode.setUpdateTime(System.currentTimeMillis());
        return flowNode;
    }

    public FlowNodeBuilder name(String name) {
        flowNode.setName(name);
        return this;
    }

    public FlowNodeBuilder titleCreator(ITitleCreator titleCreator) {
        flowNode.setTitleCreator(titleCreator);
        return this;
    }

    public FlowNodeBuilder type(NodeType type) {
        flowNode.setType(type);
        return this;
    }

    public FlowNodeBuilder view(String view) {
        flowNode.setView(view);
        return this;
    }

    public FlowNodeBuilder flowType(FlowType flowType) {
        flowNode.setFlowType(flowType);
        return this;
    }


    public FlowNodeBuilder operatorMatcher(IOperatorMatcher operatorMatcher) {
        flowNode.setOperatorMatcher(operatorMatcher);
        return this;
    }

    public FlowNodeBuilder outTrigger(IOutTrigger outTrigger) {
        flowNode.setOutTrigger(outTrigger);
        return this;
    }

    public FlowNodeBuilder next(List<FlowNode> next) {
        flowNode.setNext(next);
        return this;
    }

    public FlowNodeBuilder createUser(IFlowOperator createUser) {
        flowNode.setCreateUser(createUser);
        return this;
    }

    public FlowNodeBuilder bindData(IBindData bindData) {
        flowNode.setBindData(bindData);
        return this;
    }

    public FlowNodeBuilder errTrigger(IErrTrigger errTrigger) {
        flowNode.setErrTrigger(errTrigger);
        return this;
    }

}
