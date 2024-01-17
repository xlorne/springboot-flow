package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;

import java.util.List;

/**
 * 流程记录
 */
public interface FlowRecordRepository {

    /**
     * 保存流程记录
     *
     * @param record 流程记录
     */
    void save(FlowRecord record);

    /**
     * 查询当前步骤的所有记录，用于执行流程的会签条件判断。
     *
     * @param processId 流程id
     * @param nodeId    节点id
     * @param top       查询数量
     * @return 流程记录
     */
    List<FlowRecord> findByProcessIdOrderByCreateTimeDesc(long processId, long nodeId, int top);


    /**
     * 查询流程记录
     *
     * @param recordId 流程记录id
     * @return 流程记录
     */
    FlowRecord get(long recordId);


    /**
     * 查询流程的所有记录,用于结束流程
     *
     * @param processId 流程id
     * @return 流程记录
     */
    List<FlowRecord> findByProcessIdOrderByCreateTimeDesc(long processId);

    /**
     * 删除流程记录
     *
     * @param record 流程记录
     */
    void delete(FlowRecord record);


}
