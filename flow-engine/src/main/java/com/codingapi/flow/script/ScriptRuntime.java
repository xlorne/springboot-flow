package com.codingapi.flow.script;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.user.IFlowUser;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.List;

public class ScriptRuntime {


    public static List<FlowNode> next(String script, FlowNode node, FlowRecord... records) {
        Binding binding = new Binding();
        binding.setVariable("$node", node);
        binding.setVariable("$records", records);
        GroovyShell groovyShell = new GroovyShell(binding);
        Script userScript = groovyShell.parse(script);
        return (List<FlowNode>) userScript.run();
    }


    public static boolean match(String script, IFlowUser user) {
        Binding binding = new Binding();
        binding.setVariable("$user", user);
        GroovyShell groovyShell = new GroovyShell(binding);
        Script userScript = groovyShell.parse(script);
        return (Boolean) userScript.run();
    }
}
