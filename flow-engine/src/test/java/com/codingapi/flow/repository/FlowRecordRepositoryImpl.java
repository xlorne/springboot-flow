package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.user.IFlowUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowRecordRepositoryImpl implements FlowRecordRepository, FlowRecordQuery {

    private final Map<Long, FlowRecord> cache = new HashMap<>();

    @Override
    public void save(FlowRecord record) {
        if (record.getId() > 0) {
            cache.put(record.getId(), record);
        } else {
            long id = cache.size() + 1;
            record.setId(id);
            cache.put(id, record);
        }
    }

    @Override
    public List<FlowRecord> findByProcessIdOrderByCreateTimeDesc(long processId, long nodeId, int top) {
        List<FlowRecord> records = new ArrayList<>();
        for (FlowRecord record : cache.values()) {
            if (record.getProcessId() == processId && record.getNode().getId() == nodeId) {
                records.add(record);
            }
        }
        return records.stream()
                .sorted((o1, o2) -> (int) (o2.getCreateTime() - o1.getCreateTime()))
                .limit(top).toList();
    }

    @Override
    public FlowRecord get(long recordId) {
        return cache.get(recordId);
    }

    @Override
    public List<FlowRecord> findByProcessIdOrderByCreateTimeDesc(long processId) {
        List<FlowRecord> records = new ArrayList<>();
        for (FlowRecord record : cache.values()) {
            if (record.getProcessId() == processId) {
                records.add(record);
            }
        }
        return records.stream()
                .sorted((o1, o2) -> (int) (o2.getCreateTime() - o1.getCreateTime()))
                .toList();
    }

    @Override
    public void delete(FlowRecord record) {
        cache.remove(record.getId());
    }

    @Override
    public List<FlowRecord> findToDoList(long processId, IFlowUser flowUser) {
        List<FlowRecord> records = new ArrayList<>();
        for (FlowRecord record : cache.values()) {
            if (record.getProcessId() == processId && record.isWait()) {
                if (!record.getUsers().isEmpty()) {
                    if (record.containsUser(flowUser)) {
                        records.add(record);
                    }
                } else {
                    if (record.getNode().matchUser(flowUser)) {
                        records.add(record);
                    }
                }
            }
        }
        return records;
    }

    @Override
    public List<FlowRecord> findProcessList(long processId, IFlowUser flowUser) {
        List<FlowRecord> records = new ArrayList<>();
        for (FlowRecord record : cache.values()) {
            if (record.getProcessId() == processId && record.isApproval() && record.containsUser(flowUser)) {
                records.add(record);
            }
        }
        return records;
    }

    @Override
    public Page<FlowRecord> findToDoPage(PageRequest request, IFlowUser currentUser) {
        List<FlowRecord> records = new ArrayList<>();
        for (FlowRecord record : cache.values()) {
            if (record.isWait()) {
                if (!record.getUsers().isEmpty()) {
                    if (record.containsUser(currentUser)) {
                        records.add(record);
                    }
                } else {
                    if (record.getNode().matchUser(currentUser)) {
                        records.add(record);
                    }
                }
            }
        }
        return new PageImpl<>(records, request, records.size());
    }

    @Override
    public Page<FlowRecord> findProcessPage(PageRequest request, IFlowUser currentUser) {
        List<FlowRecord> records = new ArrayList<>();
        for (FlowRecord record : cache.values()) {
            if (record.isApproval() && record.containsUser(currentUser)) {
                records.add(record);
            }
        }
        return new PageImpl<>(records, request, records.size());
    }

    @Override
    public List<FlowRecord> findByProcessId(long processId) {
        List<FlowRecord> records = new ArrayList<>();
        for (FlowRecord record : cache.values()) {
            if (record.getProcessId() == processId) {
                records.add(record);
            }
        }
        return records;
    }
}
