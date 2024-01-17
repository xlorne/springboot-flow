package com.codingapi.flow.trigger.impl;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.trigger.IFlowTrigger;

import java.util.List;

/**
 * 结束触发器
 */
public class OverFlowTrigger implements IFlowTrigger {

    @Override
    public List<FlowNode> next(FlowNode node,FlowRecord... records) {
        return null;
    }

}
