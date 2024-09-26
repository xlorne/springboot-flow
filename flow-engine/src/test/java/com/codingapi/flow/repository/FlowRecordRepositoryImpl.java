package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.em.NodeStatus;

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
        } else {
            flowRecord.setUpdateTime(System.currentTimeMillis());
        }
    }

    @Override
    public List<FlowRecord> findAllFlowRecordByProcessId(long processId) {
        return records.stream().filter(flowRecord -> flowRecord.getProcessId() == processId).collect(Collectors.toList());
    }

    @Override
    public List<FlowRecord> findAllFlowRecordByOperatorId(long operatorId) {
        return records.stream().filter(flowRecord -> flowRecord.getOperatorUser().getId() == operatorId).collect(Collectors.toList());
    }

    @Override
    public List<FlowRecord> findTodoFlowRecordByOperatorId(long operatorId) {
        return records.stream().filter(flowRecord -> flowRecord.getOperatorUser().getId() == operatorId && flowRecord.getNodeStatus() == NodeStatus.TODO).collect(Collectors.toList());
    }

    @Override
    public List<FlowRecord> findDoneFlowRecordByOperatorId(long operatorId) {
        return records.stream().filter(flowRecord -> flowRecord.getOperatorUser().getId() == operatorId && flowRecord.getNodeStatus() == NodeStatus.DONE).collect(Collectors.toList());
    }

    @Override
    public List<FlowRecord> findChildrenFlowRecordByParentId(long parentId) {
        return records.stream().filter(flowRecord -> flowRecord.getParentId() == parentId).collect(Collectors.toList());
    }

    @Override
    public void delete(FlowRecord flowRecord) {
        records.remove(flowRecord);
    }

    @Override
    public FlowRecord getFlowRecordById(long id) {
        for (FlowRecord record : records) {
            if (record.getId() == id) {
                return record;
            }
        }
        return null;
    }
}
