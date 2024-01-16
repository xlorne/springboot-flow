package com.codingapi.flow.infrastructure.repository.impl;

import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.infrastructure.convert.FlowNodeConvertor;
import com.codingapi.flow.infrastructure.convert.FlowWorkConvertor;
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
        flowNodeEntityRepository.saveAll(FlowNodeConvertor.convert(flowWork));
    }

    @Override
    public FlowWork get(long workId) {
        return FlowWorkConvertor.convert(flowWorkEntityRepository.getReferenceById(workId));
    }
}
