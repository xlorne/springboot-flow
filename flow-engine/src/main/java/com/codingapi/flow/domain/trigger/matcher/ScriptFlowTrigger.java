package com.codingapi.flow.domain.trigger.matcher;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.trigger.IFlowTrigger;
import com.codingapi.flow.script.ScriptRuntime;

import java.util.List;

/**
 * script触发器
 */
public class ScriptFlowTrigger implements IFlowTrigger {

    private final String script;

    public ScriptFlowTrigger(String script) {
        this.script = script;
    }

    @Override
    public List<FlowNode> next(FlowNode node, FlowRecord... records) {
        return ScriptRuntime.next(script, node, records);
    }

}
