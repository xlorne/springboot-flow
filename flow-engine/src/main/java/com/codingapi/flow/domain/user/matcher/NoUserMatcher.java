package com.codingapi.flow.domain.user.matcher;

import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.domain.user.IFlowUserMatcher;

/**
 * 所有用户均不可见
 */
public class NoUserMatcher implements IFlowUserMatcher {

    @Override
    public boolean match(IFlowUser user) {
        return false;
    }
}
