package com.codingapi.flow.infrastructure.convert;

import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.gateway.FlowGatewayContext;
import com.codingapi.flow.infrastructure.context.FlowNodeContext;
import com.codingapi.flow.infrastructure.entity.FlowWorkEntity;
import com.codingapi.springboot.fast.manager.EntityManagerContent;

public class FlowWorkConvertor {


    public static FlowWork convert(FlowWorkEntity entity) {
        if(entity==null){
            return null;
        }
        FlowWork flowWork = new FlowWork();
        flowWork.setId(entity.getId());
        flowWork.setTitle(entity.getTitle());
        flowWork.setCreator(FlowGatewayContext.getInstance().getUser(entity.getCreator(),entity.getCreatorClass()));
        flowWork.setCreateTime(entity.getCreateTime());
//        flowWork.setFlow(FlowNodeContext.getInstance().getFlowNode(entity.getFlowId()));
        flowWork.setDescription(entity.getDescription());

        EntityManagerContent.getInstance().detach(entity);
        return flowWork;
    }


    public static FlowWorkEntity convert(FlowWork flowWork){
        if(flowWork ==null){
            return null;
        }
        FlowWorkEntity entity = new FlowWorkEntity();
        entity.setId(flowWork.getId());
        entity.setTitle(flowWork.getTitle());
        entity.setCreator(flowWork.getCreator().getId());
        entity.setCreateTime(flowWork.getCreateTime());
        entity.setFlowId(flowWork.getFlow().getId());
        entity.setCreatorClass(flowWork.getCreator().getClass());
        entity.setDescription(flowWork.getDescription());
        return entity;
    }

}
