package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.user.IFlowUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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


    /**
     * 代办流程记录（分页查询）
     * @param request 分页条件
     * @param currentUser 流程用户
     * @return 流程记录
     */
    Page<FlowRecord> findToDoPage(PageRequest request, IFlowUser currentUser);

    /**
     *  已办流程记录 （分页查询）
     * @param request 分页条件
     * @param currentUser 流程用户
     * @return 流程记录
     */
    Page<FlowRecord> findProcessPage(PageRequest request, IFlowUser currentUser);

}
