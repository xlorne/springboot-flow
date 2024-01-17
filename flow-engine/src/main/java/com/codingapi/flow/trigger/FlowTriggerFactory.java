package com.codingapi.flow.trigger;

import com.codingapi.flow.trigger.impl.BasicFlowTrigger;
import com.codingapi.flow.trigger.impl.OverFlowTrigger;
import com.codingapi.flow.trigger.impl.RatePassFlowTrigger;
import com.codingapi.flow.trigger.impl.ScriptFlowTrigger;

public class FlowTriggerFactory {

    /**
     * 基础条件触发器
     */
    public static IFlowTrigger basic() {
        return new BasicFlowTrigger(BasicFlowTrigger.TYPE_REJECT_NEXT);
    }

    /**
     * 基础条件触发器
     */
    public static IFlowTrigger basic(int type) {
        return new BasicFlowTrigger(type);
    }

    /**
     * 比例触发器
     */
    public static IFlowTrigger rate(float passRate) {
        return new RatePassFlowTrigger(passRate);
    }


    /**
     * 结束触发器
     */
    public static IFlowTrigger over() {
        return new OverFlowTrigger();
    }

    /**
     * script触发器
     */
    public static IFlowTrigger script(String script) {
        return new ScriptFlowTrigger(script);
    }


}
