package com.codingapi.flow.domain;

import com.codingapi.flow.data.IBindData;
import com.codingapi.flow.em.NodeStatus;
import com.codingapi.flow.operator.IFlowOperator;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程的设计，约定流程的节点配置与配置
 */
@Setter
@Getter
public class FlowWork {

    /**
     * 流程的设计id
     */
    private long id;
    /**
     * 流程标题
     */
    private String title;
    /**
     * 流程描述
     */
    private String description;
    /**
     * 流程创建者
     */
    private IFlowOperator createUser;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 更新时间
     */
    private long updateTime;
    /**
     * 是否启用
     */
    private boolean enable;
    /**
     * 是否锁定
     * 锁定流程将无法发起新的流程，当前存在的流程不受影响
     */
    private boolean lock;
    /**
     * 流程的节点(发起节点)
     */
    private FlowNode node;
    /**
     * 界面设计脚本
     */
    private String schema;

    /**
     * 创建流程节点
     *
     * @param bindData     绑定数据
     * @param operatorUser 操作者
     */
    public void createNode(IBindData bindData, IFlowOperator operatorUser) {
        node.matcherOperator(null, operatorUser);

        List<FlowRecord> records = new ArrayList<>();
        FlowRecord record = node.createRecord(null, bindData, operatorUser, operatorUser);
        record.setNodeStatus(NodeStatus.DONE);
        records.add(record);

        FlowNode nextNode = node.triggerNextNode(record);
        List<IFlowOperator> operators = nextNode.matchOperators(record);
        for(IFlowOperator operator:operators){
            records.add(nextNode.createRecord(null, bindData, operator, operatorUser));
        }

        records.forEach(r->{
            //TODO save
            System.out.println(r);
        });

    }
}
