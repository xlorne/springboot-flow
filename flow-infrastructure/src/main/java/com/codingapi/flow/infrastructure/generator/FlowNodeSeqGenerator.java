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


    public synchronized long getNextProcessId() {
        FlowNodeSeq flowNodeSeq = flowNodeSeqRepository.getById(1);
        flowNodeSeq.addNextProcessId();
        flowNodeSeqRepository.save(flowNodeSeq);
        return flowNodeSeq.getNextProcessId();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FlowNodeSeq flowNodeSeq = flowNodeSeqRepository.findById(1).orElse(null);
        if(flowNodeSeq==null) {
            flowNodeSeq = new FlowNodeSeq(1);
            flowNodeSeqRepository.save(flowNodeSeq);
        }
    }
}
