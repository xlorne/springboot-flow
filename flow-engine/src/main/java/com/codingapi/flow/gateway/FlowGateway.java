package com.codingapi.flow.gateway;

import com.codingapi.flow.bind.IBind;
import com.codingapi.flow.user.IFlowUser;

public interface FlowGateway {

    <T extends IFlowUser> T getUser(long userId,Class<T> clazz);

    <T extends IBind> T getBind(long bindId,Class<T> clazz);

}
