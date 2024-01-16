package com.codingapi.flow.gateway;

public class FlowGatewayContextRegister {

    public FlowGatewayContextRegister(FlowGateway flowGateway) {
        FlowGatewayContext.getInstance().register(flowGateway);
    }

}
