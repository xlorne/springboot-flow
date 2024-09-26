package com.codingapi.flow.matcher;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.operator.IFlowOperator;

import java.util.Arrays;
import java.util.List;

/**
 * 指定操作者匹配器
 * 传人指定操作者，返回操作者id
 */
public class SpecifyOperatorMatcher implements IOperatorMatcher {

    private final Long[] operatorIds;

    public SpecifyOperatorMatcher(long... operatorIds) {
        this.operatorIds = Arrays.stream(operatorIds).boxed().toArray(Long[]::new);
    }

    @Override
    public List<Long> matcherOperatorIds(FlowRecord context, IFlowOperator operator) {
        return Arrays.asList(operatorIds);
    }

}
