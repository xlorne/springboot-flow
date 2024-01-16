package com.codingapi.flow.infrastructure.jpa;

import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.infrastructure.entity.FlowRecordEntity;
import com.codingapi.springboot.fast.jpa.repository.FastRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlowRecordEntityRepository extends FastRepository<FlowRecordEntity, Long> {


    List<FlowRecordEntity> findByProcessId(long processId);

    @Query(value = "select r from FlowRecordEntity r where r.processId = ?1 and r.state = com.codingapi.flow.domain.em.FlowState.WAIT and r.userMatcher.match(?2) = true")
    List<FlowRecordEntity> findToDoList(long processId, IFlowUser flowUser);


    List<FlowRecordEntity> findByProcessIdAndNodeId(long processId, long nodeId);

    @Query(value = "select r from FlowRecordEntity r where r.processId = ?1 and r.state != com.codingapi.flow.domain.em.FlowState.WAIT and ?2 in (r.users) ")
    List<FlowRecordEntity> findProcessList(long processId, long userId);

}
