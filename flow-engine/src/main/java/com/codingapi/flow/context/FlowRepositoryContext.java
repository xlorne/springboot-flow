package com.codingapi.flow.context;

import com.codingapi.flow.data.BindDataSnapshot;
import com.codingapi.flow.domain.FlowNode;
import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.operator.IFlowOperator;
import com.codingapi.flow.repository.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FlowRepositoryContext {

    @Getter
    private final static FlowRepositoryContext instance = new FlowRepositoryContext();

    private FlowOperatorRepository flowOperatorRepository;
    private FlowWorkRepository flowWorkRepository;
    private FlowNodeRepository flowNodeRepository;
    private FlowRecordRepository flowRecordRepository;
    private BindDataSnapshotRepository bindDataSnapshotRepository;

    public void bind(FlowOperatorRepository flowOperatorRepository) {
        this.flowOperatorRepository = flowOperatorRepository;
    }

    public void bind(FlowWorkRepository flowWorkRepository) {
        this.flowWorkRepository = flowWorkRepository;
    }

    public void bind(FlowNodeRepository flowNodeRepository) {
        this.flowNodeRepository = flowNodeRepository;
    }

    public void bind(FlowRecordRepository flowRecordRepository) {
        this.flowRecordRepository = flowRecordRepository;
    }

    public void bind(BindDataSnapshotRepository bindDataSnapshotRepository) {
        this.bindDataSnapshotRepository = bindDataSnapshotRepository;
    }

    public List<IFlowOperator> findOperatorByIds(List<Long> operatorIds) {
        return flowOperatorRepository.findOperatorByIds(operatorIds);
    }

    public void save(FlowWork flowWork){
        flowWorkRepository.save(flowWork);
    }

    public void save(FlowRecord flowRecord){
        flowRecordRepository.save(flowRecord);
        log.info("save record:{}", flowRecord);
    }

    public void save(FlowNode flowNode){
        flowNodeRepository.save(flowNode);
    }

    public void save(BindDataSnapshot bindDataSnapshot) {
        bindDataSnapshotRepository.save(bindDataSnapshot);
    }

    public List<FlowRecord> findFlowRepositoryByProcessId(long processId) {
        return flowRecordRepository.findFlowRecordByProcessId(processId);
    }
}
