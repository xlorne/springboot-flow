package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.operator.IOperatorMatcher;
import com.codingapi.flow.operator.repository.FlowOperatorRepository;

import java.util.ArrayList;
import java.util.List;

public class FlowOperatorRepositoryImpl implements FlowOperatorRepository {

    private final List<IFlowOperator> operators = new ArrayList<>();

    public void addOperator(IFlowOperator operator) {
        operators.add(operator);
    }

    @Override
    public List<IFlowOperator> findOperator(IOperatorMatcher matcher, FlowRecord context) {
        return operators;
    }
}
