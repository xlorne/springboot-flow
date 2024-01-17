package com.codingapi.flow.em;

import com.codingapi.springboot.framework.em.IEnum;
import lombok.Getter;

@Getter
public enum FlowState implements IEnum {
    /**
     * 待执行
     */
    WAIT(0),
    /**
     * 通过
     */
    PASS(1),
    /**
     * 拒绝
     */
    REJECT(2),
    /**
     * 退回
     */
    BACK(3);

    private final int code;

    FlowState(int code) {
        this.code = code;
    }
}
