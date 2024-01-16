package com.codingapi.flow.infrastructure.context;

import com.codingapi.flow.infrastructure.jpa.FlowNodeEntityRepository;

public class FlowNodeContextRegister {

    public FlowNodeContextRegister(FlowNodeEntityRepository flowNodeEntityRepository) {
        FlowNodeContext.getInstance().register(flowNodeEntityRepository);
    }

}
