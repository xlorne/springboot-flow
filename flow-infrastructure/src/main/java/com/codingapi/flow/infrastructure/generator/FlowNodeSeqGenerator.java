package com.codingapi.flow.infrastructure.generator;

import com.codingapi.flow.infrastructure.entity.FlowNodeSeq;
import com.codingapi.flow.infrastructure.jpa.FlowNodeSeqRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;

@Transactional
@AllArgsConstructor
public class FlowNodeSeqGenerator implements InitializingBean {

    private final FlowNodeSeqRepository flowNodeSeqRepository;

    public synchronized long nextId() {
        FlowNodeSeq flowNodeSeq = flowNodeSeqRepository.getReferenceById(1);
        flowNodeSeq.addNextId();
        flowNodeSeqRepository.save(flowNodeSeq);
        return flowNodeSeq.getNextId();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        flowNodeSeqRepository.saveAndFlush(new FlowNodeSeq(1));
    }
}
