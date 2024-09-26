package com.codingapi.flow.trigger;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class ScriptOutTrigger implements IOutTrigger {

    private final String script;
    private final Object[] params;

    public ScriptOutTrigger(String script, Object... params) {
        this.script = script;
        this.params = params;
    }

    @Override
    public FlowNode trigger(FlowRecord record) {
        Binding binding = new Binding();
        binding.setVariable("record", record);
        binding.setVariable("params", params);
        GroovyShell groovyShell = new GroovyShell(binding);
        Script userScript = groovyShell.parse(script);
        return (FlowNode) userScript.run();
    }
}
