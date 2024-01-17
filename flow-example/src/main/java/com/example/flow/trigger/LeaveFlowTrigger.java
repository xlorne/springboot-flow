package com.example.flow.trigger;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.trigger.IFlowTrigger;
import com.codingapi.flow.exception.FlowServiceException;
import com.example.flow.domain.Leave;

import java.util.List;

public class LeaveFlowTrigger implements IFlowTrigger {

    @Override
    public List<FlowNode> next(FlowNode node, FlowRecord... records) {
        if (records.length == 0) {
            return null;
        }
        FlowRecord record = records[0];
        Leave leave = (Leave) record.getBind();
        if (leave == null) {
            throw new FlowServiceException("flow.bind.error", "绑定的请假数据为空");
        }
        if (leave.getDays() >= 3) {
            return node.getNextByCode("boss");
        } else {
            return node.getNextByCode("manager");
        }
    }
}
