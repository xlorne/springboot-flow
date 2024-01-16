package com.codingapi.flow.gateway;

import com.codingapi.flow.domain.IBind;
import com.codingapi.flow.domain.user.IFlowUser;
import lombok.Getter;

public class FlowGatewayContext {

    @Getter
    private final static  FlowGatewayContext instance = new FlowGatewayContext();

    private FlowGatewayContext(){}

    private FlowGateway flowGateway;

    void register(FlowGateway flowGateway){
        this.flowGateway = flowGateway;
    }

    public <T extends IFlowUser> T getUser(long userId, Class<T> clazz){
        return flowGateway.getUser(userId,clazz);
    }

    public <T extends IBind> T getBind(long bindId, Class<T> clazz){
        return flowGateway.getBind(bindId,clazz);
    }

}
