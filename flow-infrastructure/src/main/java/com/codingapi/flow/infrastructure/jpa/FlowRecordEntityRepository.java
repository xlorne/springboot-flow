package com.codingapi.flow.infrastructure.jpa;

import com.codingapi.flow.infrastructure.entity.FlowRecordEntity;
import com.codingapi.springboot.fast.jpa.repository.FastRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlowRecordEntityRepository extends FastRepository<FlowRecordEntity, Long> {


    List<FlowRecordEntity> findByProcessId(long processId);

    @Query(value = "select r from FlowRecordEntity r where r.processId = ?1 and r.state = com.codingapi.flow.domain.em.FlowState.WAIT")
    List<FlowRecordEntity> findToDoList(long processId);


    List<FlowRecordEntity> findByProcessIdAndNodeId(long processId, long nodeId);

    @Query(value = "select r from FlowRecordEntity r where r.processId = ?1 and r.state != com.codingapi.flow.domain.em.FlowState.WAIT and ?2 in (r.users) ")
    List<FlowRecordEntity> findProcessList(long processId, long userId);


    @Query(value = "select r from FlowRecordEntity r where r.state != com.codingapi.flow.domain.em.FlowState.WAIT and ?1 in (r.users) ")
    Page<FlowRecordEntity> findProcessPage(long userId, PageRequest request);


    @Query(value = "select r from FlowRecordEntity r where r.state = com.codingapi.flow.domain.em.FlowState.WAIT and r.workId in (?1) and r.nodeId in (?2)")
    Page<FlowRecordEntity> findToDoPage(List<Long> workIds, List<Long> nodeIds, PageRequest request);

}
