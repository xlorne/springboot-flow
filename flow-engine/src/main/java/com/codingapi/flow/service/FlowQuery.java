package com.codingapi.flow.service;


import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.repository.FlowRecordQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class FlowQuery {

    private final FlowRecordQuery flowRecordQuery;


    /**
     * 获取待办列表
     *
     * @param processId   流程id
     * @param currentUser 当前用户
     * @return 待办列表
     */
    public List<FlowRecord> todos(long processId, IFlowUser currentUser) {
        return flowRecordQuery.findToDoList(processId, currentUser);
    }


    /**
     * 获取已办列表
     *
     * @param processId   流程id
     * @param currentUser 当前用户
     * @return 已办列表
     */
    public List<FlowRecord> process(long processId, IFlowUser currentUser) {
        return flowRecordQuery.findProcessList(processId, currentUser);
    }


}
