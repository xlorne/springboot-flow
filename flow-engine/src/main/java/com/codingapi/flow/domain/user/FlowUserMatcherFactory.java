package com.codingapi.flow.domain.user;

import com.codingapi.flow.domain.user.matcher.AnyUserMatcher;
import com.codingapi.flow.domain.user.matcher.NoUserMatcher;
import com.codingapi.flow.domain.user.matcher.ScriptUserMatcher;
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

    /**
     * 指定用户匹配器
     */
    public static IFlowUserMatcher users(List<? extends IFlowUser> users) {
        return new SpecifyUserMatcher(users);
    }

    /**
     * script匹配器
     */
    public static ScriptUserMatcher script(String script) {
        return new ScriptUserMatcher(script);
    }


}
