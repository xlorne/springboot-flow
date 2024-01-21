package com.codingapi.flow.repository;

import com.codingapi.flow.domain.FlowWork;

/**
 * 流程工作
 */
public interface FlowWorkRepository {

    void save(FlowWork flowWork);

    FlowWork get(long workId);

    void delete(long workId);



}
