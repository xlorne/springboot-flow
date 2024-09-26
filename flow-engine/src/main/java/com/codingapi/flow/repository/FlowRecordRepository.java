package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;

import java.util.List;

public interface FlowRecordRepository {

    void save(FlowRecord flowRecord);

    List<FlowRecord> findFlowRecordByProcessId(long processId);

    List<FlowRecord> findFlowRecordByOperatorId(long operatorId);
}
