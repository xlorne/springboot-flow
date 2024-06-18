package com.codingapi.flow.infrastructure.gateway.impl;

import com.codingapi.flow.gateway.FlowProcessIdGeneratorGateway;
import com.codingapi.flow.infrastructure.generator.FlowNodeSeqGenerator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FlowProcessIdGeneratorGatewayImpl implements FlowProcessIdGeneratorGateway {

    private final FlowNodeSeqGenerator flowNodeSeqGenerator;

    @Override
    public long createProcessId() {
        return flowNodeSeqGenerator.getNextProcessId();
    }
}
