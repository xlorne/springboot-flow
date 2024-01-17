package com.codingapi.flow.infrastructure.repository.impl;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.infrastructure.convert.FlowRecordConvertor;
import com.codingapi.flow.infrastructure.entity.FlowRecordEntity;
import com.codingapi.flow.infrastructure.jpa.FlowRecordEntityRepository;
import com.codingapi.flow.repository.FlowRecordRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FlowRecordRepositoryImpl implements FlowRecordRepository {

    private final FlowRecordEntityRepository flowRecordEntityRepository;

    @Override
    public void save(FlowRecord record) {
        FlowRecordEntity entity = FlowRecordConvertor.convert(record);
        entity = flowRecordEntityRepository.save(entity);
        record.setId(entity.getId());
    }


    @Override
    public List<FlowRecord> findAll(long processId, long nodeId) {
        return flowRecordEntityRepository.findByProcessIdAndNodeId(processId, nodeId).stream().map(FlowRecordConvertor::convert).collect(Collectors.toList());
    }

    @Override
    public FlowRecord get(long recordId) {
        return FlowRecordConvertor.convert(flowRecordEntityRepository.getReferenceById(recordId));
    }


    @Override
    public List<FlowRecord> findAll(long processId) {
        return flowRecordEntityRepository.findByProcessId(processId).stream().map(FlowRecordConvertor::convert).collect(Collectors.toList());
    }

    @Override
    public void delete(FlowRecord record) {
        flowRecordEntityRepository.deleteById(record.getId());
    }
}
