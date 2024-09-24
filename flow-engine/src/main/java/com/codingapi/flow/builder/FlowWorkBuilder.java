package com.codingapi.flow.builder;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.operator.IFlowOperator;

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

    public FlowWorkBuilder node(FlowNode node) {
        flowWork.setNode(node);
        return this;
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

}
