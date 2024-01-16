package com.codingapi.flow.infrastructure.jpa;

import com.codingapi.flow.infrastructure.entity.FlowNodeEntity;
import com.codingapi.springboot.fast.jpa.repository.FastRepository;

public interface FlowNodeEntityRepository extends FastRepository<FlowNodeEntity, Long> {

}
