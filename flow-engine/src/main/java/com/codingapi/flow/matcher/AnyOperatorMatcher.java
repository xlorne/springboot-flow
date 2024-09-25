package com.codingapi.flow.matcher;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.operator.IFlowOperator;

import java.util.List;

public class AnyOperatorMatcher implements IOperatorMatcher {

    @Override
    public List<Long> matcherOperatorIds(FlowRecord context, IFlowOperator operator) {
        return List.of(operator.getId());
    }

}
