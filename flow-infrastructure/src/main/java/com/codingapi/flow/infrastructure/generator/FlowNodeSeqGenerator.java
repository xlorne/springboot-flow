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

    public synchronized long getNextRecordId() {
        FlowNodeSeq flowNodeSeq = flowNodeSeqRepository.getReferenceById(1);
        flowNodeSeq.addNextRecordId();
        flowNodeSeqRepository.save(flowNodeSeq);
        return flowNodeSeq.getNextRecordId();
    }

    public synchronized long getNextNodeId() {
        FlowNodeSeq flowNodeSeq = flowNodeSeqRepository.getReferenceById(1);
        flowNodeSeq.addNextNodeId();
        flowNodeSeqRepository.save(flowNodeSeq);
        return flowNodeSeq.getNextNodeId();
    }

    public synchronized long getNextProcessId() {
        FlowNodeSeq flowNodeSeq = flowNodeSeqRepository.getReferenceById(1);
        flowNodeSeq.addNextProcessId();
        flowNodeSeqRepository.save(flowNodeSeq);
        return flowNodeSeq.getNextProcessId();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        flowNodeSeqRepository.saveAndFlush(new FlowNodeSeq(1));
    }
}
