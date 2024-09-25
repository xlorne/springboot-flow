package com.codingapi.flow.matcher;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.operator.IFlowOperator;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SpecifyOperatorMatcher implements IOperatorMatcher {

    private final long operatorId;

    @Override
    public List<Long> matcherOperatorIds(FlowRecord context, IFlowOperator operator) {
        return List.of(operatorId);
    }

}
