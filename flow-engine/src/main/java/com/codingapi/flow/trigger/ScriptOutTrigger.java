package com.codingapi.flow.trigger;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.script.ScriptRuntime;

public class ScriptOutTrigger implements IOutTrigger {

    private final String script;
    private final Object[] params;

    public ScriptOutTrigger(String script, Object... params) {
        this.script = script;
        this.params = params;
    }

    @Override
    public FlowNode trigger(FlowRecord record) {
        return ScriptRuntime.run(script, binding -> {
            binding.setVariable("record", record);
            binding.setVariable("params", params);
        }, FlowNode.class);
    }
}
