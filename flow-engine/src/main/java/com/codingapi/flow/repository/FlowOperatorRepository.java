package com.codingapi.flow.repository;

import com.codingapi.flow.operator.IFlowOperator;

import java.util.List;

public interface FlowOperatorRepository {

    List<? extends IFlowOperator> findOperatorByIds(List<Long> operatorIds);

    IFlowOperator getOperatorById(long id);

}
