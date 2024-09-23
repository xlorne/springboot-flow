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

    public FlowWorkBuilder setTitle(String title) {
        flowWork.setTitle(title);
        return this;
    }

    public FlowWorkBuilder setDescription(String description) {
        flowWork.setDescription(description);
        return this;
    }

    public FlowWorkBuilder setCreateUser(IFlowOperator createUser) {
        flowWork.setCreateUser(createUser);
        return this;
    }

    public FlowWorkBuilder setNode(FlowNode node) {
        flowWork.setNode(node);
        return this;
    }

    public FlowWorkBuilder setSchema(String schema) {
        flowWork.setSchema(schema);
        return this;
    }

    public FlowWorkBuilder setEnable(boolean enable) {
        flowWork.setEnable(enable);
        return this;
    }

    public FlowWorkBuilder setLock(boolean lock) {
        flowWork.setLock(lock);
        return this;
    }

    public FlowWork build() {
        flowWork.setUpdateTime(System.currentTimeMillis());
        return flowWork;
    }

}
