package com.codingapi.flow.matcher;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.operator.IFlowOperator;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

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
        Binding binding = new Binding();
        binding.setVariable("context", context);
        binding.setVariable("operator", operator);
        binding.setVariable("params", params);
        GroovyShell groovyShell = new GroovyShell(binding);
        Script userScript = groovyShell.parse(script);
        return (List<Long>) userScript.run();
    }
}
