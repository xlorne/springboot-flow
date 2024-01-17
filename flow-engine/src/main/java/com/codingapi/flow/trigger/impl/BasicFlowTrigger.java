package com.codingapi.flow.trigger.impl;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.em.FlowState;
import com.codingapi.flow.trigger.IFlowTrigger;

import java.util.Collections;
import java.util.List;

/**
 * 串行触发器
 * 两种模式，默认为拒绝执行下一个节点。即{@link #TYPE_REJECT_NEXT}模式
 * 1. {@link #TYPE_REJECT_NEXT} 拒绝执行下一个节点
 * 2. {@link #TYPE_REJECT_BACK} 退回到上一个节点
 * {@link FlowState#PASS} 通过执行下一个节点
 * {@link FlowState#REJECT} 拒绝执行下一个节点
 * {@link FlowState#BACK} 退回到上一个节点
 */
public class BasicFlowTrigger implements IFlowTrigger {

    public static final int TYPE_REJECT_NEXT = 0;
    public static final int TYPE_REJECT_BACK = 1;

    private final int type;

    public BasicFlowTrigger(int type) {
        this.type = type;
    }

    public BasicFlowTrigger() {
        this.type = TYPE_REJECT_NEXT;
    }

    @Override
    public List<FlowNode> next(FlowNode node, FlowRecord... records) {
        FlowRecord record = records[0];
        FlowState flowState = record.getState();
        if (flowState == FlowState.PASS) {
            return node.getNext();
        }
        if (flowState == FlowState.REJECT) {
            if (type == TYPE_REJECT_NEXT) {
                return node.getNext();
            } else {
                return Collections.singletonList(node.getPrev());
            }
        }
        if (flowState == FlowState.BACK) {
            return Collections.singletonList(node.getPrev());
        }
        return null;
    }

}
