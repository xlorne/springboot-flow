package com.codingapi.flow.infrastructure.convert;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.infrastructure.context.FlowNodeContext;
import com.codingapi.flow.infrastructure.entity.FlowNodeEntity;
import com.codingapi.springboot.fast.manager.EntityManagerContent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FlowNodeConvertor {


    public static FlowNode convert(FlowNodeEntity entity) {
        if (entity == null) {
            return null;
        }

        FlowNode flowNode = new FlowNode();
        flowNode.setId(entity.getId());
        flowNode.setName(entity.getName());
        flowNode.setFlowType(entity.getFlowType());
        flowNode.setPrev(FlowNodeContext.getInstance().getFlowNode(entity.getPrev()));
        flowNode.setNext(entity.getNext().stream().map(FlowNodeContext.getInstance()::getFlowNode).collect(Collectors.toList()));
        flowNode.setTrigger(entity.getTrigger());
        flowNode.setUserMatcher(entity.getUserMatcher());
        flowNode.setCode(entity.getCode());
        flowNode.setCount(entity.getCount());

        EntityManagerContent.getInstance().detach(entity);
        return flowNode;
    }


    public static FlowNodeEntity convert(FlowNode node) {
        if (node == null) {
            return null;
        }
        FlowNodeEntity entity = new FlowNodeEntity();
        entity.setId(node.getId());
        entity.setName(node.getName());
        entity.setFlowType(node.getFlowType());
        entity.setPrev(node.getPrev().getId());
        entity.setNext(node.getNext().stream().map(FlowNode::getId).collect(Collectors.toList()));
        entity.setTrigger(node.getTrigger());
        entity.setUserMatcher(node.getUserMatcher());
        entity.setCode(node.getCode());
        entity.setCount(node.getCount());


        return entity;
    }

    public static List<FlowNodeEntity> convert(FlowWork flowWork) {
        FlowNode root = flowWork.getFlow();
        List<FlowNodeEntity> entities = new ArrayList<>();

        Consumer<FlowNode> consumer = new Consumer<>() {
            @Override
            public void accept(FlowNode flowNode) {
                flowNode.getNext().forEach(this);
                entities.add(convert(flowNode));
            }
        };

        root.getNext().forEach(consumer);
        entities.add(convert(root));
        return entities;
    }
}
