package com.codingapi.flow.gateway;

import java.util.concurrent.atomic.AtomicLong;

public class FlowProcessIdGeneratorGatewayImpl implements FlowProcessIdGeneratorGateway {

    private final AtomicLong processId = new AtomicLong(1);

    @Override
    public long createProcessId() {
        return processId.getAndIncrement();
    }
}
