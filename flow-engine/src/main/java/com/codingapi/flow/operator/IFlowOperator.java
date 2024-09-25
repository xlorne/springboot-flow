package com.codingapi.flow.operator;

import com.codingapi.flow.domain.FlowRecord;

/**
 * 操作者，流程的操作者，只要实现这个接口，就可以作为流程的操作者
 */
public interface IFlowOperator {
    /**
     * 获取操作者的id
     *
     * @return 操作者的id
     */
    long getId();

    /**
     * 获取操作者的名称
     *
     * @return 操作者的名称
     */
    String getName();


    /**
     * 匹配操作者
     * @param context 流程记录
     * @return 是否匹配
     */
    boolean matcher(FlowRecord context);
}
