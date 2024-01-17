package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.user.IFlowUser;

import java.util.List;

public interface FlowRecordQuery {

    /**
     * 查询代办流程记录
     * @param processId 流程id
     * @param flowUser 流程用户
     * @return 流程记录
     */
    List<FlowRecord> findToDoList(long processId, IFlowUser flowUser);

    /**
     * 查询已办流程记录
     * @param processId 流程id
     * @param flowUser 流程用户
     * @return 流程记录
     */
    List<FlowRecord> findProcessList(long processId, IFlowUser flowUser);

}
