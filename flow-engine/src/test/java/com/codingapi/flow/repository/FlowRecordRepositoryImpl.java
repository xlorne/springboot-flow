package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;

import java.util.ArrayList;
import java.util.List;

public class FlowRecordRepositoryImpl implements FlowRecordRepository{

    private final List<FlowRecord> records = new ArrayList<>();

    @Override
    public void save(FlowRecord flowRecord) {
        records.add(flowRecord);
        if(flowRecord.getId()==0){
            flowRecord.setId(records.size());
        }

    }
}
