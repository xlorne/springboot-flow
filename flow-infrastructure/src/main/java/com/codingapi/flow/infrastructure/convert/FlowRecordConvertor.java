package com.codingapi.flow.infrastructure.convert;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.user.IFlowUser;
import com.codingapi.flow.gateway.FlowGatewayContext;
import com.codingapi.flow.infrastructure.context.FlowNodeContext;
import com.codingapi.flow.infrastructure.entity.FlowRecordEntity;
import com.codingapi.springboot.fast.manager.EntityManagerContent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlowRecordConvertor {


    public static FlowRecord convert(FlowRecordEntity entity) {
        if (entity == null || entity.getId() == null) {
            return null;
        }

        FlowRecord record = new FlowRecord();
        record.setId(entity.getId());
        record.setCreateTime(entity.getCreateTime());
        record.setUpdateTime(entity.getUpdateTime());
        record.setFinish(entity.getFinish());
        record.setName(entity.getName());
        record.setProcessId(entity.getProcessId());
        record.setState(entity.getState());
        record.setWorkId(entity.getWorkId());
        record.setBind(entity.getBindId() != null ? FlowGatewayContext.getInstance().getBind(entity.getBindId(), entity.getBindClass()) : null);
        record.setUsers(entity.getUsers() != null && !entity.getUsers().isEmpty() ? entity.getUsers().stream().map(userId -> FlowGatewayContext.getInstance().getUser(userId, entity.getUserClass())).collect(Collectors.toList()) : new ArrayList<>());
        record.setOpinion(entity.getOpinion());
        record.setNode(FlowNodeContext.getInstance().getFlowNode(entity.getNodeId()));

        EntityManagerContent.getInstance().detach(entity);
        return record;
    }


    public static FlowRecordEntity convert(FlowRecord record) {
        if (record == null) {
            return null;
        }
        FlowRecordEntity entity = new FlowRecordEntity();
        entity.setId(record.getId());
        entity.setCreateTime(record.getCreateTime());
        entity.setUpdateTime(record.getUpdateTime());
        entity.setFinish(record.isFinish());
        entity.setName(record.getName());
        entity.setProcessId(record.getProcessId());
        entity.setState(record.getState());
        entity.setWorkId(record.getWorkId());
        entity.setBindId(record.getBind() != null ? record.getBind().getId() : null);
        entity.setBindClass(record.getBind() != null ? record.getBind().getClass() : null);
        entity.setUsers(record.getUsers().stream().filter(Objects::nonNull).map(IFlowUser::getId).collect(Collectors.toList()));
        entity.setUserClass(record.getUsers().stream().map(IFlowUser::getClass).findFirst().orElse(null));
        entity.setOpinion(record.getOpinion());
        entity.setNodeId(record.getNode().getId());

        return entity;
    }

}
