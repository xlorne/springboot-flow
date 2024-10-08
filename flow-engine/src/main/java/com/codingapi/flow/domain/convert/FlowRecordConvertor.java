package com.codingapi.flow.domain.convert;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.bind.IBind;
import com.codingapi.flow.em.FlowState;

public class FlowRecordConvertor {

    public static FlowRecord convert(long processId, long workId, FlowNode flowNode, IBind bind) {
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setProcessId(processId);
        flowRecord.setWorkId(workId);
        flowRecord.setName(flowNode.getName());
        flowRecord.setNode(flowNode);
        flowRecord.setState(FlowState.WAIT);
        flowRecord.setCreateTime(System.currentTimeMillis());
        flowRecord.setBind(bind);
        return flowRecord;
    }
}
