package com.codingapi.flow.infrastructure.repository.impl;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.infrastructure.convert.FlowRecordConvertor;
import com.codingapi.flow.infrastructure.convert.FlowWorkConvertor;
import com.codingapi.flow.infrastructure.jpa.FlowRecordEntityRepository;
import com.codingapi.flow.infrastructure.jpa.FlowWorkEntityRepository;
import com.codingapi.flow.repository.FlowRecordQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


@AllArgsConstructor
public class FlowRecordQueryImpl implements FlowRecordQuery {

    private final FlowRecordEntityRepository flowRecordEntityRepository;

    private final FlowWorkEntityRepository flowWorkEntityRepository;

    @Override
    public List<FlowRecord> findToDoList(long processId, IFlowUser flowUser) {
        return flowRecordEntityRepository.findToDoList(processId).stream().map(FlowRecordConvertor::convert)
                .filter(record -> record.getNode().matchUser(flowUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowRecord> findProcessList(long processId, IFlowUser flowUser) {
        return flowRecordEntityRepository.findProcessList(processId, flowUser.getId()).stream().map(FlowRecordConvertor::convert)
                .collect(Collectors.toList());
    }

    private List<Long> matchUserNodes(FlowNode flow, IFlowUser currentUser) {
        List<Long> nodeIds = new ArrayList<>();
        Consumer<FlowNode> consumer = new Consumer<>() {
            @Override
            public void accept(FlowNode flowNode) {
                if (flowNode.matchUser(currentUser)) {
                    nodeIds.add(flowNode.getId());
                }
                if (flowNode.getNext() != null) {
                    flowNode.getNext().forEach(this);
                }
            }
        };
        consumer.accept(flow);
        return nodeIds;
    }

    @Override
    public Page<FlowRecord> findToDoPage(PageRequest request, IFlowUser currentUser) {
        List<FlowWork> flowWorks = flowWorkEntityRepository.findAll().stream().map(FlowWorkConvertor::convert).toList();
        List<Long> nodeIds = new ArrayList<>();
        List<Long> wordIds = new ArrayList<>();
        for (FlowWork flowWork : flowWorks) {
            if (!wordIds.contains(flowWork.getId())) {
                wordIds.add(flowWork.getId());
            }

            List<Long> nodeItemIds = matchUserNodes(flowWork.getFlow(),currentUser);
            for (Long nodeId : nodeItemIds) {
                if (!nodeIds.contains(nodeId)) {
                    nodeIds.add(nodeId);
                }
            }
        }
        return flowRecordEntityRepository.findToDoPage(wordIds, nodeIds,currentUser.getId(), request).map(FlowRecordConvertor::convert);
    }

    @Override
    public Page<FlowRecord> findProcessPage(PageRequest request, IFlowUser currentUser) {
        return flowRecordEntityRepository.findProcessPage(currentUser.getId(), request).map(FlowRecordConvertor::convert);
    }

    @Override
    public List<FlowRecord> findByProcessId(long processId) {
        return flowRecordEntityRepository.findByProcessId(processId).stream().map(FlowRecordConvertor::convert).collect(Collectors.toList());
    }
}
