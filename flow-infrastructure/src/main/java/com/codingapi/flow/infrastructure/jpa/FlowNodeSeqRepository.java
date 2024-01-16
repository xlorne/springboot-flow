package com.codingapi.flow.infrastructure.jpa;

import com.codingapi.flow.infrastructure.entity.FlowNodeSeq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowNodeSeqRepository extends JpaRepository<FlowNodeSeq,Integer> {

}
