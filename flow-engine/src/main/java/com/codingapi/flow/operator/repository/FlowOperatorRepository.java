package com.codingapi.flow.operator.repository;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.operator.IOperatorMatcher;

import java.util.List;

public interface FlowOperatorRepository {

    List<IFlowOperator> findOperator(IOperatorMatcher matcher, FlowRecord context);
}
