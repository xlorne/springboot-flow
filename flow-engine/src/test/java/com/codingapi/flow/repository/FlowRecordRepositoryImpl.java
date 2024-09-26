package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlowRecordRepositoryImpl implements FlowRecordRepository {

    private final List<FlowRecord> records = new ArrayList<>();

    @Override
    public void save(FlowRecord flowRecord) {
        if (flowRecord.getId() == 0) {
            records.add(flowRecord);
            flowRecord.setId(records.size());
        }else {
            flowRecord.setUpdateTime(System.currentTimeMillis());
        }
    }

    @Override
    public List<FlowRecord> findFlowRecordByProcessId(long processId) {
        return records.stream().filter(flowRecord -> flowRecord.getProcessId() == processId).collect(Collectors.toList());
    }

    @Override
    public List<FlowRecord> findFlowRecordByOperatorId(long operatorId) {
        return records.stream().filter(flowRecord -> flowRecord.getOperatorUser().getId() == operatorId).collect(Collectors.toList());
    }
}
