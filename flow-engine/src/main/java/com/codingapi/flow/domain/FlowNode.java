package com.codingapi.flow.domain;

import com.codingapi.flow.creator.ITitleCreator;
import com.codingapi.flow.data.IBindData;
import com.codingapi.flow.em.*;
import com.codingapi.flow.operator.FlowOperatorContext;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.operator.IOperatorMatcher;
import com.codingapi.flow.trigger.IErrTrigger;
import com.codingapi.flow.trigger.IOutTrigger;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    public static final String CODE_START = "start";
    public static final String CODE_OVER = "over";

    public static final String VIEW_DEFAULT = "default";

    /**
     * 节点id
     */
    private long id;

    /**
     * 节点编码
     */
    private String code;

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
     * 设计者
     */
    private IFlowOperator createUser;
    /**
     * 异常触发器，当流程发生异常时异常通常是指找不到审批人，将会触发异常触发器，异常触发器可以是一个节点
     */
    private IErrTrigger errTrigger;


    /**
     * 添加下一个节点
     *
     * @param flowNode 下一个节点
     */
    public void addNextNode(FlowNode flowNode) {
        if (next == null) {
            this.next = new ArrayList<>();
        }
        List<String> nextCodes = this.next.stream().map(FlowNode::getCode).toList();
        if (!nextCodes.contains(flowNode.getCode())) {
            this.next.add(flowNode);
        }
    }

    /**
     * 匹配操作者
     *
     * @param operator 操作者
     */
    public void matcherOperator(FlowRecord flowRecord, IFlowOperator operator) {
        if (!operator.matcher(flowRecord)) {
            throw new RuntimeException("operator not match");
        }
    }

    public FlowRecord createRecord(String opinion,
                                   IBindData bindData,
                                   IFlowOperator operatorUser,
                                   IFlowOperator createOperatorUser) {
        FlowRecord record = new FlowRecord();
        record.bindData(bindData);
        record.setProcessId(System.currentTimeMillis());
        record.setNode(this);
        record.setOpinion(opinion);
        record.setOperatorUser(operatorUser);
        record.setCreateTime(System.currentTimeMillis());
        record.setCreateOperatorUser(createOperatorUser);
        record.setNodeStatus(NodeStatus.TODO);
        record.setFlowStatus(FlowStatus.RUNNING);
        record.setState(RecodeState.NORMAL);
        record.setTitle(createTitle(record));

        return record;
    }


    /**
     * 创建标题
     *
     * @param record 流程记录
     * @return 标题
     */
    public String createTitle(FlowRecord record) {
        return this.titleCreator.createTitle(record);
    }

    public FlowNode triggerNextNode(FlowRecord record) {
        if (outTrigger != null) {
            return outTrigger.trigger(record);
        }
        return null;
    }

    public boolean isOver() {
        return CODE_OVER.equals(this.code);
    }

    public boolean isStart() {
        return CODE_START.equals(this.code);
    }

    public List<IFlowOperator> matchOperators(FlowRecord record) {
        return FlowOperatorContext.getInstance().match(this.operatorMatcher,record);
    }

    public FlowNode getNextNodeByCode(String depart) {
        if (next != null) {
            for (FlowNode node : next) {
                if (node.getCode().equals(depart)) {
                    return node;
                }
            }
        }
        return null;
    }
}
