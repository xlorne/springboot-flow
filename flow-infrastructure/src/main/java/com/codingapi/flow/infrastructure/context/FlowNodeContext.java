package com.codingapi.flow.infrastructure.context;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.infrastructure.convert.FlowNodeConvertor;
import com.codingapi.flow.infrastructure.entity.FlowNodeEntity;
import com.codingapi.flow.infrastructure.jpa.FlowNodeEntityRepository;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FlowNodeContext {

    @Getter
    private static final FlowNodeContext instance = new FlowNodeContext();

    private FlowNodeContext() {
    }

    private FlowNodeEntityRepository flowNodeEntityRepository;

    void register(FlowNodeEntityRepository flowNodeEntityRepository) {
        this.flowNodeEntityRepository = flowNodeEntityRepository;
    }

    public FlowNode getFlowNode(Long nodeId) {
        FlowNode node = loadFlowNode(nodeId);
        FlowNodeEntity entity = flowNodeEntityRepository.getFlowNodeEntityById(nodeId);
        if (entity.getPrev() != null) {
            node.setPrev(FlowNodeConvertor.convert(flowNodeEntityRepository.getFlowNodeEntityById(entity.getPrev())));
        }
        return node;
    }

    private FlowNode loadFlowNode(long nodeId) {
        FlowNodeEntity entity = flowNodeEntityRepository.getFlowNodeEntityById(nodeId);
        FlowNode node = FlowNodeConvertor.convert(entity);
        if (entity.getNext() != null) {
            List<FlowNode> next = new ArrayList<>();
            for (long nextId : entity.getNext()) {
                next.add(loadFlowNode(nextId));
            }
            node.setNext(next);
        }
        return node;
    }

}
