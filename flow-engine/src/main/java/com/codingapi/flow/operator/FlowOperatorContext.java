package com.codingapi.flow.operator;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.operator.repository.FlowOperatorRepository;
import lombok.Getter;

import java.util.List;

public class FlowOperatorContext {

    @Getter
    private final static FlowOperatorContext instance = new FlowOperatorContext();

    private FlowOperatorRepository flowOperatorRepository;

    public void bind(FlowOperatorRepository flowOperatorRepository){
        this.flowOperatorRepository = flowOperatorRepository;
    }

    public List<IFlowOperator> match(IOperatorMatcher matcher, FlowRecord context){
        return flowOperatorRepository.findOperator(matcher,context);
    }

}
