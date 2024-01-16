package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;

import java.util.List;

/**
 * 流程记录
 */
public interface FlowRecordRepository {

    void save(FlowRecord record);

    List<FlowRecord> findToDoList(long processId);

    List<FlowRecord> findAll(long processId, long nodeId);

    FlowRecord get(long recordId);

    List<FlowRecord> findProcessList(long processId);

    List<FlowRecord> findAll(long processId);

    void delete(FlowRecord record);

}
