package com.codingapi.flow.infrastructure.repository.impl;

import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.infrastructure.convert.FlowNodeConvertor;
import com.codingapi.flow.infrastructure.convert.FlowWorkConvertor;
import com.codingapi.flow.infrastructure.entity.FlowNodeEntity;
import com.codingapi.flow.infrastructure.entity.FlowWorkEntity;
import com.codingapi.flow.infrastructure.jpa.FlowNodeEntityRepository;
import com.codingapi.flow.infrastructure.jpa.FlowWorkEntityRepository;
import com.codingapi.flow.repository.FlowWorkRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FlowWorkRepositoryImpl implements FlowWorkRepository {

    private final FlowWorkEntityRepository flowWorkEntityRepository;

    private final FlowNodeEntityRepository flowNodeEntityRepository;

    @Override
    public void save(FlowWork flowWork) {
        FlowWorkEntity entity = FlowWorkConvertor.convert(flowWork);
        entity = flowWorkEntityRepository.save(entity);
        flowWork.setId(entity.getId());
        this.saveNode(flowWork.getFlow(), flowWork.getId());
    }


    private void saveNode( FlowNode node,long workId) {
        FlowNodeEntity entity =  FlowNodeConvertor.convert(node, workId);
        entity = flowNodeEntityRepository.save(entity);
        node.setId(entity.getId());

        if(node.getNext()!=null) {
            node.getNext().forEach(next -> {
                saveNode(next, workId);
            });
        }
    }

    @Override
    public FlowWork get(long workId) {
        return FlowWorkConvertor.convert(flowWorkEntityRepository.getFlowWorkEntityById(workId));
    }

    @Override
    public void delete(long workId) {
        flowWorkEntityRepository.deleteById(workId);
    }
}
