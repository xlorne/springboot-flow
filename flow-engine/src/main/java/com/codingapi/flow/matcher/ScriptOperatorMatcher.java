package com.codingapi.flow.matcher;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.script.ScriptRuntime;

import java.util.List;

public class ScriptOperatorMatcher implements IOperatorMatcher {

    private final String script;
    private final Object[] params;

    public ScriptOperatorMatcher(String script, Object... params) {
        this.script = script;
        this.params = params;
    }

    @Override
    public List<Long> matcherOperatorIds(FlowRecord context, IFlowOperator operator) {
        return ScriptRuntime.run(script,
                binding -> {
                    binding.setVariable("context", context);
                    binding.setVariable("operator", operator);
                    binding.setVariable("params", params);
                },
                List.class);
    }
}
