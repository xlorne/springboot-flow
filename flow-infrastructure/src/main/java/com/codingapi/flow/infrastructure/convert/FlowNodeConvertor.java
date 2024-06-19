package com.codingapi.flow.infrastructure.convert;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.infrastructure.entity.FlowNodeEntity;
import com.codingapi.springboot.fast.manager.EntityManagerContent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FlowNodeConvertor {



    public static FlowNode convert(FlowNodeEntity entity) {
        if (entity == null || entity.getId() == null) {
            return null;
        }

        FlowNode flowNode = new FlowNode();
        flowNode.setId(entity.getId());
        flowNode.setName(entity.getName());
        flowNode.setFlowType(entity.getFlowType());
        flowNode.setFlowTrigger(entity.getFlowTrigger());
        flowNode.setUserMatcher(entity.getUserMatcher());
        flowNode.setCode(entity.getCode());
        flowNode.setCount(entity.getCount());

        EntityManagerContent.getInstance().detach(entity);
        return flowNode;
    }


    public static FlowNodeEntity convert(FlowNode node, long workId) {
        if (node == null) {
            return null;
        }
        FlowNodeEntity entity = new FlowNodeEntity();
        entity.setId(node.getId());
        entity.setName(node.getName());
        entity.setFlowType(node.getFlowType());
        entity.setPrev(node.getPrev() != null ? node.getPrev().getId() : null);
        entity.setNext(node.getNext().stream().map(FlowNode::getId).collect(Collectors.toList()));
        entity.setFlowTrigger(node.getFlowTrigger());
        entity.setUserMatcher(node.getUserMatcher());
        entity.setCode(node.getCode());
        entity.setCount(node.getCount());
        entity.setWorkId(workId);


        return entity;
    }

}
