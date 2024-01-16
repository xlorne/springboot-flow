package com.codingapi.flow.domain.trigger;


import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;

import java.io.Serializable;
import java.util.List;

/**
 * 流程触发器
 */
public interface IFlowTrigger extends Serializable {

    /**
     * 执行触发器
     *  判断流程的下一个节点
     *  如果返回null,则流程尚未执行完成，通常是在会签的时候需要
     *
     * @param records 流程记录
     * @return 下一个节点
     */
    List<FlowNode> next(FlowNode node, FlowRecord... records);

}
