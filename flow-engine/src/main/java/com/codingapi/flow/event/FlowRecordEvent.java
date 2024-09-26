package com.codingapi.flow.event;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.springboot.framework.event.IEvent;

/**
 * 流程记录事件
 */
public record FlowRecordEvent(FlowRecord record) implements IEvent {

}
