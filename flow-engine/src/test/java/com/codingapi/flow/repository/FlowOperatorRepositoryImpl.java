package com.codingapi.flow.repository;

import com.codingapi.flow.operator.IFlowOperator;

import java.util.ArrayList;
import java.util.List;

public class FlowOperatorRepositoryImpl implements FlowOperatorRepository {

    private final List<IFlowOperator> operators = new ArrayList<>();

    public void addOperator(IFlowOperator operator) {
        if(operator.getId()==0){
            operators.add(operator);
            operator.setId(operators.size());
        }
    }


    @Override
    public List<IFlowOperator> findOperatorByIds(List<Long> operatorIds) {
        return operators.stream().filter(operator -> operatorIds.contains(operator.getId())).toList();
    }
}
