package com.codingapi.flow.domain.user.matcher;

import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.domain.user.IFlowUserMatcher;
import com.codingapi.flow.script.ScriptRuntime;

/**
 * script匹配器
 */
public class ScriptUserMatcher implements IFlowUserMatcher {

    private final String script;

    public ScriptUserMatcher(String script) {
        this.script = script;
    }

    @Override
    public boolean match(IFlowUser user) {
        return ScriptRuntime.match(script, user);
    }
}
