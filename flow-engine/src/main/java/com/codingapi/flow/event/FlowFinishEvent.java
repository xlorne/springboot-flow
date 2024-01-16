package com.codingapi.flow.event;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.springboot.framework.event.IEvent;
import lombok.Getter;

/**
 * 流程结束事件
 */
@Getter
public class FlowFinishEvent implements IEvent {

    private final FlowNode flowNode;
    private final FlowRecord flowRecord;
    private final long createTime;

    public FlowFinishEvent(FlowNode flowNode, FlowRecord flowRecord) {
        this.flowNode = flowNode;
        this.flowRecord = flowRecord;
        this.createTime = System.currentTimeMillis();
    }

}
