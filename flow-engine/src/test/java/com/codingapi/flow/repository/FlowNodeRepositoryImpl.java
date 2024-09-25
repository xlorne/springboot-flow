package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowNode;

import java.util.ArrayList;
import java.util.List;

public class FlowNodeRepositoryImpl implements FlowNodeRepository{

    private final List<FlowNode> nodes = new ArrayList<>();

    @Override
    public void save(FlowNode flowNode) {
        nodes.add(flowNode);
        if(flowNode.getId()==0){
            flowNode.setId(nodes.size());
        }
    }
}
