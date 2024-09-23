package com.codingapi.flow.script;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class ScriptRuntime {

    public static boolean match(String script, Object bind) {
        Binding binding = new Binding();
        binding.setVariable("bind", bind);
        GroovyShell groovyShell = new GroovyShell(binding);
        Script userScript = groovyShell.parse(script);
        return (Boolean) userScript.run();
    }
}
