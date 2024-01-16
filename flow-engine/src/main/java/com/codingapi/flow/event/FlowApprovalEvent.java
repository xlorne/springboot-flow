package com.codingapi.flow.event;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.springboot.framework.event.IEvent;
import lombok.Getter;

/**
 * 流程审批事件
 */
@Getter
public class FlowApprovalEvent implements IEvent {

    private final FlowNode flowNode;
    private final FlowRecord flowRecord;
    private final IFlowUser flowUser;
    private final long createTime;

    public FlowApprovalEvent(FlowNode flowNode, FlowRecord flowRecord, IFlowUser flowUser) {
        this.flowNode = flowNode;
        this.flowRecord = flowRecord;
        this.flowUser = flowUser;
        this.createTime = System.currentTimeMillis();
    }

}
