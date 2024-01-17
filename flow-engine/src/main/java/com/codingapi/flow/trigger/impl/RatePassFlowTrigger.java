package com.codingapi.flow.trigger.impl;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.em.FlowState;
import com.codingapi.flow.trigger.IFlowTrigger;

import java.util.Collections;
import java.util.List;

/**
 * 通过比例条件触发器
 * 仅当通过的比例达到一定的比例时，才会执行下一个节点
 */
public class RatePassFlowTrigger implements IFlowTrigger {

    private final float passRate;

    public RatePassFlowTrigger(float passRate) {
        this.passRate = passRate;
    }

    @Override
    public List<FlowNode> next(FlowNode node,FlowRecord... records) {
        int passCount = 0;
        for (FlowRecord record : records) {
            if (record.getState() == FlowState.PASS) {
                passCount++;
            }
            if (record.getState() == FlowState.BACK) {
                return Collections.singletonList(node.getPrev());
            }
        }
        if (passCount >= records.length * passRate) {
            return node.getNext();
        }
        return null;
    }

}
