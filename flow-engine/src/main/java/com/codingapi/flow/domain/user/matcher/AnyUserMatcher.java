package com.codingapi.flow.domain.user.matcher;

import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.domain.user.IFlowUserMatcher;

/**
 * 所有用户均可见
 */
public class AnyUserMatcher implements IFlowUserMatcher {

    @Override
    public boolean match(IFlowUser user) {
        return true;
    }
}
