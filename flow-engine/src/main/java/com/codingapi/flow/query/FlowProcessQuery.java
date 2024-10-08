package com.codingapi.flow.query;


import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.user.IFlowUser;
import com.codingapi.flow.repository.FlowRecordQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class FlowProcessQuery {

    private final FlowRecordQuery flowRecordQuery;


    /**
     * 获取我的发起列表
     *
     * @param request   分页展示
     * @param currentUser 当前用户
     * @return 发起列表
     */
    public Page<FlowRecord> myInitiatives(PageRequest request, IFlowUser currentUser) {
        return flowRecordQuery.myInitiatives(request,currentUser);
    }

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
     * 获取待办列表
     *
     * @param request   分页展示
     * @param currentUser 当前用户
     * @return 待办列表
     */
    public Page<FlowRecord> todos(PageRequest request, IFlowUser currentUser) {
        return flowRecordQuery.findToDoPage(request,currentUser);
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


    /**
     * 获取已办列表
     *
     * @param request   分页展示
     * @param currentUser 当前用户
     * @return 已办列表
     */
    public Page<FlowRecord> process(PageRequest request, IFlowUser currentUser) {
        return flowRecordQuery.findProcessPage(request, currentUser);
    }


    /**
     * 获取流程详情
     * @param processId 流程id
     * @return 流程详情
     */
    public List<FlowRecord> detail(long processId) {
        return flowRecordQuery.findByProcessId(processId);
    }
}
