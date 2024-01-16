package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.user.IFlowUser;

import java.util.List;

/**
 * 流程记录
 */
public interface FlowRecordRepository {

    void save(FlowRecord record);

    List<FlowRecord> findToDoList(long processId, IFlowUser flowUser);

    /**
     * 查询当前步骤的所有记录，用于执行流程的会签条件判断。
     *
     * @param processId 流程id
     * @param nodeId    节点id
     * @return 流程记录
     */
    List<FlowRecord> findAll(long processId, long nodeId);

    FlowRecord get(long recordId);

    List<FlowRecord> findProcessList(long processId, IFlowUser flowUser);

    /**
     * 查询流程的所有记录,用于结束流程
     *
     * @param processId 流程id
     * @return 流程记录
     */
    List<FlowRecord> findAll(long processId);

    void delete(FlowRecord record);

}
