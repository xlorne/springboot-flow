package com.codingapi.flow.builder;

import com.codingapi.flow.creator.DefaultTitleCreator;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.em.NodeType;
import com.codingapi.flow.matcher.IOperatorMatcher;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.trigger.IErrTrigger;
import com.codingapi.flow.trigger.IOutTrigger;
import com.codingapi.flow.trigger.OverOutTrigger;
import lombok.AllArgsConstructor;

public class FlowNodeFactory {

    @AllArgsConstructor
    public static class Builder {
        private IFlowOperator createUser;

        public FlowNode startNode(String name, IOperatorMatcher operatorMatcher, IOutTrigger outTrigger) {
            return startNode(name, FlowNode.VIEW_DEFAULT, operatorMatcher, outTrigger);
        }


        public FlowNode startNode(String name, String view, IOperatorMatcher operatorMatcher, IOutTrigger outTrigger) {
            FlowNode flowNode = new FlowNode();
            flowNode.setName(name);
            flowNode.setType(NodeType.START);
            flowNode.setCode(FlowNode.CODE_START);
            flowNode.setView(view);
            flowNode.setTitleCreator(new DefaultTitleCreator());
            flowNode.setFlowType(FlowType.NOT_SIGN);
            flowNode.setCreateTime(System.currentTimeMillis());
            flowNode.setUpdateTime(System.currentTimeMillis());
            flowNode.setCreateUser(createUser);
            flowNode.setOutOperatorMatcher(operatorMatcher);
            flowNode.setOutTrigger(outTrigger);
            return flowNode;
        }

        public FlowNode overNode(String name) {
            FlowNode flowNode = new FlowNode();
            flowNode.setName(name);
            flowNode.setType(NodeType.OVER);
            flowNode.setCode(FlowNode.CODE_OVER);
            flowNode.setView(FlowNode.VIEW_DEFAULT);
            flowNode.setOutTrigger(new OverOutTrigger());
            flowNode.setTitleCreator(new DefaultTitleCreator());
            flowNode.setFlowType(FlowType.NOT_SIGN);
            flowNode.setCreateTime(System.currentTimeMillis());
            flowNode.setUpdateTime(System.currentTimeMillis());
            flowNode.setCreateUser(createUser);
            return flowNode;
        }

        public FlowNode node(String name,
                             String code,
                             String view,
                             FlowType flowType,
                             IOutTrigger outTrigger,
                             IOperatorMatcher outOperatorMatcher) {
            return node(name, code, view, flowType, outTrigger, outOperatorMatcher, null, null);
        }


        public FlowNode node(String name,
                             String code,
                             FlowType flowType,
                             IOutTrigger outTrigger,
                             IOperatorMatcher outOperatorMatcher) {
            return node(name, code, FlowNode.VIEW_DEFAULT, flowType, outTrigger, outOperatorMatcher, null, null);
        }

        public FlowNode node(String name,
                             String code,
                             String view,
                             FlowType flowType,
                             IOutTrigger outTrigger,
                             IOperatorMatcher outOperatorMatcher,
                             IErrTrigger errTrigger,
                             IOperatorMatcher errOperatorMatcher) {
            FlowNode flowNode = new FlowNode();
            flowNode.setName(name);
            flowNode.setType(NodeType.APPROVAL);
            flowNode.setCode(code);
            flowNode.setView(view);
            flowNode.setTitleCreator(new DefaultTitleCreator());
            flowNode.setFlowType(flowType);
            flowNode.setCreateTime(System.currentTimeMillis());
            flowNode.setUpdateTime(System.currentTimeMillis());
            flowNode.setCreateUser(createUser);

            flowNode.setOutTrigger(outTrigger);
            flowNode.setOutOperatorMatcher(outOperatorMatcher);

            flowNode.setErrTrigger(errTrigger);
            flowNode.setErrOperatorMatcher(errOperatorMatcher);

            return flowNode;
        }

    }

    public static Builder Builder(IFlowOperator createUser) {
        return new Builder(createUser);
    }


}
