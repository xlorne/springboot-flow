package com.codingapi.flow.infrastructure.context;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.infrastructure.convert.FlowNodeConvertor;
import com.codingapi.flow.infrastructure.jpa.FlowNodeEntityRepository;
import lombok.Getter;

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
        return FlowNodeConvertor.convert(flowNodeEntityRepository.getReferenceById(nodeId));
    }

}
