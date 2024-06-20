package com.codingapi.flow.service;


import com.codingapi.flow.bind.IBind;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.domain.convert.FlowRecordConvertor;
import com.codingapi.flow.em.FlowState;
import com.codingapi.flow.em.FlowType;
import com.codingapi.flow.event.FlowApprovalEvent;
import com.codingapi.flow.event.FlowFinishEvent;
import com.codingapi.flow.event.FlowRecallEvent;
import com.codingapi.flow.event.FlowStartEvent;
import com.codingapi.flow.exception.FlowServiceException;
import com.codingapi.flow.gateway.FlowProcessIdGeneratorGateway;
import com.codingapi.flow.repository.FlowRecordRepository;
import com.codingapi.flow.repository.FlowWorkRepository;
import com.codingapi.flow.user.IFlowUser;
import com.codingapi.springboot.framework.event.EventPusher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class FlowService {

    private final FlowWorkRepository flowWorkRepository;

    private final FlowRecordRepository flowRecordRepository;

    private final FlowProcessIdGeneratorGateway flowProcessIdGeneratorGateway;

    /**
     * 创建流程
     *
     * @param flowWork 流程
     * @return 流程workId
     */
    public long save(FlowWork flowWork) {
        flowWorkRepository.save(flowWork);
        log.info("save flowWork:{}", flowWork);
        return flowWork.getId();
    }

    /**
     * 删除流程
     */
    public void delete(long workId) {
        flowWorkRepository.delete(workId);
    }

    /**
     * 创建流程
     *
     * @param workId 流程id
     * @param user   用户
     * @param bind   绑定数据
     * @return 流程id
     */
    public long createFlow(long workId, IFlowUser user, IBind bind) {
        FlowWork flowWork = flowWorkRepository.get(workId);
        FlowNode flowNode = flowWork.getFlow();
        if (flowNode.matchUser(user)) {
            FlowState state = FlowState.PASS;
            //创建流程记录
            long processId = flowProcessIdGeneratorGateway.createProcessId();
            FlowRecord flowRecord = FlowRecordConvertor.convert(processId, workId, flowNode, bind);
            flowRecord.approval(user, state, null);
            flowRecordRepository.save(flowRecord);
            //触发流程
            this.flowRunning(flowRecord, state);

            FlowStartEvent flowEvent = new FlowStartEvent(flowNode, flowRecord, user);
            EventPusher.push(flowEvent);
            return flowRecord.getProcessId();
        } else {
            throw new FlowServiceException("user.create.error", "当前用户无权限创建该流程");
        }
    }

    /**
     * 创建流程
     *
     * @param workId 流程id
     * @param user   用户
     * @return 流程id
     */
    public long createFlow(long workId, IFlowUser user) {
        return this.createFlow(workId, user, null);
    }


    /**
     * 流程审批
     *
     * @param flowRecord 流程记录
     * @param state      审批状态
     */
    private void flowRunning(FlowRecord flowRecord, FlowState state) {
        FlowNode current = flowRecord.getNode();
        List<FlowNode> next = null;
        if (current.isParallel()) {
            // 并行流程
            // 获取当前流程的所有记录
            List<FlowRecord> recordList = flowRecordRepository.findByProcessIdOrderByCreateTimeDesc(flowRecord.getProcessId(), current.getId(), current.getCount());

            // 获取下一步流程
            next = current.trigger(recordList.toArray(new FlowRecord[]{}));
            if (next != null) {
                //当触发执行下一步的操作的时候，清空当前状态没有完成的记录。
                recordList.stream().filter(record -> record.getState() == FlowState.WAIT).forEach(record -> {
                    record.approval(null, state, null);
                    flowRecordRepository.save(record);
                });
            }
        } else {
            //串行流程

            // 获取下一步流程
            next = current.trigger(flowRecord);
        }
        // 下一步流程记录
        if (next != null) {
            next.forEach(node -> {
                if (node.getFlowType() == FlowType.OVER) {
                    FlowRecord nextRecord = FlowRecordConvertor.convert(flowRecord.getProcessId(), flowRecord.getWorkId(), node, flowRecord.getBind());
                    nextRecord.finish();
                    flowRecordRepository.save(nextRecord);

                    // 流程结束
                    List<FlowRecord> flowRecords = flowRecordRepository.findByProcessIdOrderByCreateTimeDesc(flowRecord.getProcessId());
                    for (FlowRecord record : flowRecords) {
                        record.setFinish(true);
                        flowRecordRepository.save(record);

                        FlowFinishEvent flowEvent = new FlowFinishEvent(node, record);
                        EventPusher.push(flowEvent);
                    }
                } else {
                    // 创建下一步流程记录。即添加todo记录
                    for (int i = 0; i < node.getCount(); i++) {
                        FlowRecord nextRecord = FlowRecordConvertor.convert(flowRecord.getProcessId(), flowRecord.getWorkId(), node, flowRecord.getBind());

                        // 如果是回退，则需要指定回退用户。
                        if (state == FlowState.BACK) {
                            List<FlowRecord> flowRecordList = flowRecordRepository.findByProcessIdOrderByCreateTimeDesc(flowRecord.getProcessId(), node.getId(), node.getCount());
                            List<IFlowUser> backUsers = new ArrayList<>();
                            for (FlowRecord record : flowRecordList) {
                                backUsers.addAll(record.getUsers());
                            }
                            nextRecord.setUsers(backUsers);
                        }

                        flowRecordRepository.save(nextRecord);
                    }
                }
            });
        }


    }

    /**
     * 审批通过
     *
     * @param recordId 流程记录id
     * @param option   审批意见
     * @param user     审批用户
     */
    public void pass(long recordId, String option, IFlowUser user) {
        this.approval(recordId, FlowState.PASS, option, user);
    }


    /**
     * 审批撤回
     *
     * @param recordId 流程记录id
     * @param user     操作用户
     */
    public void recall(long recordId, IFlowUser user) {
        FlowRecord flowRecord = flowRecordRepository.get(recordId);
        if (flowRecord == null) {
            throw new FlowServiceException("flow.recall.error", "流程不存在");
        }
        if (flowRecord.isFinish()) {
            throw new FlowServiceException("flow.recall.error", "流程已经结束");
        }
        if (!flowRecord.canApproval(user)) {
            throw new FlowServiceException("flow.recall.error", "当前用户不能撤回该流程");
        }
        // 当前记录已经被自己审批过了
        List<FlowRecord> recallRecords = new ArrayList<>();
        // 获取下一步流程的记录状态
        List<FlowNode> flowNodes = flowRecord.getNode().getNext();
        for (FlowNode flowNode : flowNodes) {
            List<FlowRecord> flowRecords = flowRecordRepository.findByProcessIdOrderByCreateTimeDesc(flowRecord.getProcessId(), flowNode.getId(), flowNode.getCount());
            for (FlowRecord record : flowRecords) {
                if (record.getState() != FlowState.WAIT) {
                    throw new FlowServiceException("flow.recall.error", "流程已经被审批，无法撤回");
                }
                recallRecords.add(record);
            }
        }
        for (FlowRecord record : recallRecords) {
            flowRecordRepository.delete(record);
        }
        flowRecord.recall();
        flowRecordRepository.save(flowRecord);

        FlowRecallEvent flowEvent = new FlowRecallEvent(flowRecord.getNode(), flowRecord, user);
        EventPusher.push(flowEvent);
    }


    /**
     * 审批拒绝
     *
     * @param recordId 流程记录id
     * @param option   审批意见
     * @param user     审批用户
     */
    public void reject(long recordId, String option, IFlowUser user) {
        this.approval(recordId, FlowState.REJECT, option, user);
    }

    /**
     * 审批退回
     *
     * @param recordId 流程记录id
     * @param option   审批意见
     * @param user     审批用户
     */
    public void back(long recordId, String option, IFlowUser user) {
        this.approval(recordId, FlowState.BACK, option, user);
    }


    /**
     * 审批流程
     *
     * @param recordId 流程记录id
     * @param option   审批意见
     * @param user     审批用户
     */
    public void approval(long recordId, FlowState state, String option, IFlowUser user) {
        FlowRecord flowRecord = flowRecordRepository.get(recordId);
        if (flowRecord == null) {
            throw new FlowServiceException("flow.approval.error", "流程不存在");
        }
        if (flowRecord.isApproval()) {
            throw new FlowServiceException("flow.approval.error", "该流程已经被审批过了");
        }
        if (flowRecord.isFinish()) {
            throw new FlowServiceException("flow.approval.error", "该流程已经结束了");
        }
        if (!flowRecord.getNode().matchUser(user)) {
            throw new FlowServiceException("flow.approval.error", "当前用户不能审批该流程");
        }
        // 撤回流程的时候，只能是撤回的用户自己能够继续提交，不再验证FlowTrigger的用户匹配器
        if (flowRecord.getUsers() != null && !flowRecord.getUsers().isEmpty()) {
            if (!flowRecord.containsUser(user)) {
                throw new FlowServiceException("flow.approval.error", "当前用户不能审批该流程");
            }
        }

        flowRecord.approval(user, state, option);
        flowRecordRepository.save(flowRecord);
        //触发流程
        this.flowRunning(flowRecord, state);

        FlowApprovalEvent flowEvent = new FlowApprovalEvent(flowRecord.getNode(), flowRecord, user);
        EventPusher.push(flowEvent);

    }


}
