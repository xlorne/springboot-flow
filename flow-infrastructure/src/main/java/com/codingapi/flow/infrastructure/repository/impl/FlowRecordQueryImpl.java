package com.codingapi.flow.infrastructure.repository.impl;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.infrastructure.convert.FlowRecordConvertor;
import com.codingapi.flow.infrastructure.jpa.FlowRecordEntityRepository;
import com.codingapi.flow.repository.FlowRecordQuery;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class FlowRecordQueryImpl implements FlowRecordQuery {

    private final FlowRecordEntityRepository flowRecordEntityRepository;

    @Override
    public List<FlowRecord> findToDoList(long processId, IFlowUser flowUser) {
        return flowRecordEntityRepository.findToDoList(processId).stream().map(FlowRecordConvertor::convert)
                .filter(record -> record.getNode().matchUser(flowUser))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowRecord> findProcessList(long processId, IFlowUser flowUser) {
        return flowRecordEntityRepository.findProcessList(processId, flowUser.getId()).stream().map(FlowRecordConvertor::convert)
                .collect(Collectors.toList());
    }

}
