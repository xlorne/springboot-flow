package com.codingapi.flow.domain.user;

import com.codingapi.flow.domain.user.matcher.AnyUserMatcher;
import com.codingapi.flow.domain.user.matcher.NoUserMatcher;
import com.codingapi.flow.domain.user.matcher.SpecifyUserMatcher;

import java.util.List;

public class FlowUserMatcherFactory {

    /**
     * 所有用户均可见
     */
    public static IFlowUserMatcher anyUsers() {
        return new AnyUserMatcher();
    }

    /**
     * 所有用户均不可见
     */
    public static IFlowUserMatcher noUsers() {
        return new NoUserMatcher();
    }

    /**
     * 指定用户匹配器
     */
    public static IFlowUserMatcher users(IFlowUser... users) {
        return new SpecifyUserMatcher(List.of(users));
    }

}
