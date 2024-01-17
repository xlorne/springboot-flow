package com.codingapi.flow.domain.trigger;

import com.codingapi.flow.domain.trigger.matcher.BasicFlowTrigger;
import com.codingapi.flow.domain.trigger.matcher.OverFlowTrigger;
import com.codingapi.flow.domain.trigger.matcher.RatePassFlowTrigger;

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


}
