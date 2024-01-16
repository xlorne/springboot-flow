package com.codingapi.flow.domain.em;

import com.codingapi.springboot.framework.em.IEnum;
import lombok.Getter;

/**
 * 流程类型
 */
@Getter
public enum FlowType implements IEnum {

    /**
     * 串行
     */
    SERIAL(1),
    /**
     * 并行
     */
    PARALLEL(2),
    /**
     * 结束
     */
    OVER(3);

    private final int code;

    FlowType(int code) {
        this.code = code;
    }
}
