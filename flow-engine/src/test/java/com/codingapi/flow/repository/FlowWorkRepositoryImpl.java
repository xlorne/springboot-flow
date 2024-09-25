package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowWork;

import java.util.ArrayList;
import java.util.List;

public class FlowWorkRepositoryImpl implements FlowWorkRepository{

    private final List<FlowWork> works = new ArrayList<>();

    @Override
    public void save(FlowWork flowWork) {
        works.add(flowWork);
        if(flowWork.getId()==0){
            flowWork.setId(works.size());
        }

    }
}
