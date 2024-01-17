package com.codingapi.flow.user;

import java.io.Serializable;

public interface IFlowUserMatcher extends Serializable {

    boolean match(IFlowUser user);

}
