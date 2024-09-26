package com.codingapi.flow.domain;

import com.codingapi.flow.context.FlowRepositoryContext;
import com.codingapi.flow.data.BindDataSnapshot;
import com.codingapi.flow.data.IBindData;
import com.codingapi.flow.em.FlowStatus;
import com.codingapi.flow.em.NodeStatus;
import com.codingapi.flow.em.RecodeState;
import com.codingapi.flow.operator.IFlowOperator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 流程记录，记录流程的执行记录。数据中有关键的三个id，分别是流程设计id、节点id、流程id、流程记录id
 * 流程设计id是对应FlowWork的id
 * 节点id是对应FlowNode的id
 * 流程id是在每个流程发起以后生成的一个id
 * 流程记录id是流程在执行过程中的一个记录id
 */
@Setter
@Getter
@ToString
@Slf4j
public class FlowRecord {

    /**
     * 流程记录id
     */
    private long id;
    /**
     * 工作id
     */
    private long workId;
    /**
     * 流程id
     */
    private long processId;
    /**
     * 节点
     */
    private FlowNode node;
    /**
     * 流程标题
     */
    private String title;
    /**
     * 操作者
     */
    private IFlowOperator operatorUser;
    /**
     * 节点状态 | 待办、已办、专办
     */
    private NodeStatus nodeStatus;
    /**
     * 创建时间
     */
    private long createTime;
    /**
     * 发起者id
     */
    private IFlowOperator createOperatorUser;
    /**
     * 审批意见
     */
    private String opinion;
    /**
     * 流程状态 ｜ 进行中、已完成
     */
    private FlowStatus flowStatus;
    /**
     * 记录状态 | 正常、异常，当流程发生异常时，将会记录异常状态，异常状态的流程将无法继续审批
     */
    private RecodeState state;
    /**
     * 异常信息
     */
    private String errMessage;
    /**
     * 绑定数据的快照
     */
    private BindDataSnapshot bindDataSnapshot;

    /**
     * 绑定数据
     *
     * @param bindData 绑定数据
     */
    public void bindData(IBindData bindData) {
        if (bindData != null) {
            this.bindDataSnapshot = new BindDataSnapshot();
            this.bindDataSnapshot.setBindDataClassName(bindData.getClass().getName());
            this.bindDataSnapshot.setCreateTime(System.currentTimeMillis());
            this.bindDataSnapshot.setSnapshot(bindData.toJsonSnapshot());
            FlowRepositoryContext.getInstance().save(this.bindDataSnapshot);
        }
    }


    /**
     * 获取绑定数据
     *
     * @return 绑定数据
     */
    public IBindData getBindData() {
        if (bindDataSnapshot != null) {
            return bindDataSnapshot.toBindData();
        }
        return null;
    }


    /**
     * 提交流程
     *
     * @param bindData 绑定数据
     */
    public void submit(String opinion, IBindData bindData) {
        FlowNode nextNode = node.triggerNextNode(this);
        // 是否为结束节点
        if (nextNode.isOver()) {
            FlowRecord nextRecord = nextNode.createRecord(processId, bindData, operatorUser, createOperatorUser);
            nextRecord.setState(RecodeState.NORMAL);
            nextRecord.setNodeStatus(NodeStatus.DONE);
            nextRecord.setFlowStatus(FlowStatus.FINISH);

            List<FlowRecord> flowRecords = FlowRepositoryContext.getInstance().findFlowRepositoryByProcessId(processId);
            if (!flowRecords.isEmpty()) {
                for (FlowRecord flowRecord : flowRecords) {
                    flowRecord.setFlowStatus(FlowStatus.FINISH);
                    FlowRepositoryContext.getInstance().save(flowRecord);
                }
            }
            FlowRepositoryContext.getInstance().save(nextRecord);
        } else {
            // 获取下一个节点的操作者
            List<IFlowOperator> operators = nextNode.matchOutOperators(this);
            if (operators.isEmpty()) {
                nextNode = nextNode.triggerErrorNode(this);
                operators = nextNode.matchErrorOperators(this);
                if (operators.isEmpty()) {
                    throw new RuntimeException("next node operator not found.");
                }
            }
            // 创建下一个节点的记录
            for (IFlowOperator operator : operators) {
                FlowRecord nextRecord = nextNode.createRecord(processId, bindData, operator, createOperatorUser);
                FlowRepositoryContext.getInstance().save(nextRecord);
            }
        }
        this.opinion = opinion;
        this.nodeStatus = NodeStatus.DONE;
        FlowRepositoryContext.getInstance().save(this);
    }
}
