package com.codingapi.flow.gateway;

import com.codingapi.flow.bind.IBind;
import com.codingapi.flow.user.IFlowUser;

public class FlowGatewayImpl implements FlowGateway{

    @Override
    public <T extends IFlowUser> T getUser(long userId, Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends IBind> T getBind(long bindId, Class<T> clazz) {
        return null;
    }
}
