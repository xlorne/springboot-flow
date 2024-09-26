package com.codingapi.flow.builder;

import com.codingapi.flow.context.FlowRepositoryContext;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.operator.IFlowOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlowWorkBuilder {

    private final FlowWork flowWork = new FlowWork();

    private FlowWorkBuilder() {
        flowWork.setEnable(true);
        flowWork.setLock(false);
        flowWork.setCreateTime(System.currentTimeMillis());
    }

    public static FlowWorkBuilder Builder() {
        return new FlowWorkBuilder();
    }

    public FlowWorkBuilder title(String title) {
        flowWork.setTitle(title);
        return this;
    }

    public FlowWorkBuilder description(String description) {
        flowWork.setDescription(description);
        return this;
    }

    public FlowWorkBuilder createUser(IFlowOperator createUser) {
        flowWork.setCreateUser(createUser);
        return this;
    }

    public FlowNodeBuilder nodeBuilder() {
        return new FlowNodeBuilder();
    }

    public FlowWorkBuilder schema(String schema) {
        flowWork.setSchema(schema);
        return this;
    }

    public FlowWorkBuilder enable(boolean enable) {
        flowWork.setEnable(enable);
        return this;
    }

    public FlowWorkBuilder lock(boolean lock) {
        flowWork.setLock(lock);
        return this;
    }

    public FlowWork build() {
        flowWork.setUpdateTime(System.currentTimeMillis());
        return flowWork;
    }

    public class Relations{
        private final List<FlowNode> list;

        private Relations(List<FlowNode> list) {
            this.list = list;
        }

        public Relations relation(String... codes) {
            relationNodes(codes);
            return this;
        }

        public FlowWork build() {
            FlowNode flowNode = getFlowNodeByCode(FlowNode.CODE_START);
            if(flowNode==null){
                throw new RuntimeException("start node not found");
            }
            flowWork.setNode(flowNode);
            FlowRepositoryContext.getInstance().save(flowWork);
            list.forEach(FlowRepositoryContext.getInstance()::save);
            return flowWork;
        }

        private FlowNode getFlowNodeByCode(String code) {
            for (FlowNode flowNode : list) {
                if (flowNode.getCode().equals(code)) {
                    return flowNode;
                }
            }
            return null;
        }

        private void relationNodes(String[] codes) {
            int length = codes.length;
            if (length >= 2) {
                String first = codes[0];
                FlowNode firstNode = getFlowNodeByCode(first);
                if (firstNode == null) {
                    throw new RuntimeException(first+" not found node");
                }
                String next = codes[1];
                FlowNode nexNode = getFlowNodeByCode(next);
                if (nexNode == null) {
                    throw new RuntimeException(next+" not found node");
                }
                firstNode.addNextNode(nexNode);
                relationNodes(Arrays.copyOfRange(codes, 1, length));
            }
        }
    }


    public class FlowNodeBuilder {

        private final List<FlowNode> list = new ArrayList<>();

        private FlowNodeBuilder() {
        }

        public FlowNodeBuilder addNode(FlowNode flowNode) {
            flowNode.setFlowWork(flowWork);
            list.add(flowNode);
            return this;
        }

        public Relations relations() {
            return new Relations(list);
        }


    }


}
