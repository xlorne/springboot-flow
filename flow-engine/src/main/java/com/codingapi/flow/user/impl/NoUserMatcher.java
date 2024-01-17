package com.codingapi.flow.user.impl;

import com.codingapi.flow.user.IFlowUser;
import com.codingapi.flow.user.IFlowUserMatcher;

/**
 * 所有用户均不可见
 */
public class NoUserMatcher implements IFlowUserMatcher {

    @Override
    public boolean match(IFlowUser user) {
        return false;
    }
}
