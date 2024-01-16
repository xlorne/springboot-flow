package com.codingapi.flow.gateway;

import com.codingapi.flow.domain.IBind;
import com.codingapi.flow.domain.user.IFlowUser;

public interface FlowGateway {

    <T extends IFlowUser> T getUser(long userId,Class<T> clazz);

    <T extends IBind> T getBind(long bindId,Class<T> clazz);

}
