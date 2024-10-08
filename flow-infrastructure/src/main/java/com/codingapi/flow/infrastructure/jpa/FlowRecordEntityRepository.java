package com.codingapi.flow.infrastructure.jpa;

import com.codingapi.flow.infrastructure.entity.FlowRecordEntity;
import com.codingapi.springboot.fast.jpa.repository.FastRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlowRecordEntityRepository extends FastRepository<FlowRecordEntity, Long> {


    @Query(value = "select r from FlowRecordEntity r where r.processId = ?1 order by r.createTime desc")
    List<FlowRecordEntity> findByProcessId(long processId);

    @Query(value = "select r from FlowRecordEntity r where r.processId = ?1 and r.state = com.codingapi.flow.em.FlowState.WAIT")
    List<FlowRecordEntity> findToDoList(long processId);


    @Query(value = "select r from FlowRecordEntity r where r.processId = ?1 and r.nodeId = ?2 order by r.createTime desc")
    List<FlowRecordEntity> findByProcessIdAndNodeId(long processId, long nodeId, PageRequest request);

    @Query(value = "select r from FlowRecordEntity r where r.processId = ?1 and r.state != com.codingapi.flow.em.FlowState.WAIT and ?2 in (r.users) ")
    List<FlowRecordEntity> findProcessList(long processId, long userId);


    @Query(value = "select r from FlowRecordEntity r where r.state != com.codingapi.flow.em.FlowState.WAIT and ?1 in (r.users) order by r.createTime desc")
    Page<FlowRecordEntity> findProcessPage(long userId, PageRequest request);


    @Query(value = "select r from FlowRecordEntity r where (r.state = com.codingapi.flow.em.FlowState.WAIT and r.workId in (?1) and r.nodeId in (?2) and r.users is null) or (r.state = com.codingapi.flow.em.FlowState.WAIT and ?3 in (r.users)) order by r.createTime desc")
    Page<FlowRecordEntity> findToDoPage(List<Long> workIds, List<Long> nodeIds,long userId,PageRequest request);


    FlowRecordEntity getFlowRecordEntityById(long id);

}
