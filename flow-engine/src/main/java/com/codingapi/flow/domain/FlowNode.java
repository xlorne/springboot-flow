package com.codingapi.flow.domain;

import com.codingapi.flow.creator.ITitleCreator;
import com.codingapi.flow.data.IBindData;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.em.NodeType;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.operator.IOperatorMatcher;
import com.codingapi.flow.trigger.IErrTrigger;
import com.codingapi.flow.trigger.IOutTrigger;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 流程节点，约定流程的节点执行逻辑
 * 流程的节点类型：会签、非会签，会签代表所有审批人都需要审批通过，非会签代表只需要一个审批人审批通过即可
 * 流程的出口配置，约定流程的出口配置
 * 流程的操作者配置，约定流程的操作者配置
 */
@Setter
@Getter
public class FlowNode {

    /**
     * 节点id
     */
    private long id;

    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点标题创建规则
     */
    private ITitleCreator titleCreator;
    /**
     * 节点类型 | 分为发起、审批、结束
     */
    private NodeType type;
    /**
     * 节点视图
     */
    private String view;
    /**
     * 流程审批类型 | 分为会签、非会签
     */
    private FlowType flowType;
    /**
     * 操作者匹配器
     */
    private IOperatorMatcher operatorMatcher;
    /**
     * 出口触发器
     */
    private IOutTrigger outTrigger;
    /**
     * 下一个节点数组，系统将根据出口配置，选择下一个节点
     */
    private List<FlowNode> next;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 更新时间
     */
    private long updateTime;
    /**
     * 设计者id
     */
    private IFlowOperator createUser;
    /**
     * 绑定数据的id
     */
    private IBindData bindData;
    /**
     * 异常触发器，当流程发生异常时异常通常是指找不到审批人，将会触发异常触发器，异常触发器可以是一个节点
     */
    private IErrTrigger errTrigger;

}
