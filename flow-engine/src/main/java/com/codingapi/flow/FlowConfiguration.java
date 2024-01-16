package com.codingapi.flow;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.domain.IBind;
import com.codingapi.flow.domain.em.FlowState;
import com.codingapi.flow.domain.user.IFlowUser;
import com.codingapi.flow.gateway.FlowGateway;
import com.codingapi.flow.gateway.FlowGatewayContextRegister;
import com.codingapi.flow.gennerate.FlowIdGeneratorRegister;
import com.codingapi.flow.gennerate.SnowflakeIDGenerator;
import com.codingapi.flow.repository.FlowRecordRepository;
import com.codingapi.flow.repository.FlowWorkRepository;
import com.codingapi.flow.service.FlowService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class FlowConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public FlowWorkRepository flowWorkRepository() {
        return new FlowWorkRepository() {

            private final Map<Long, FlowWork> cache = new HashMap<>();

            @Override
            public void save(FlowWork flowWork) {
                cache.put(flowWork.getId(), flowWork);
            }

            @Override
            public FlowWork get(long workId) {
                return cache.get(workId);
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public FlowRecordRepository flowRecordRepository() {
        return new FlowRecordRepository() {
            private final Map<Long, List<FlowRecord>> cache = new HashMap<>();
            private final Map<Long, FlowRecord> records = new HashMap<>();


            private boolean hasRecord(FlowRecord record, List<FlowRecord> recordList) {
                for (FlowRecord flowRecord : recordList) {
                    if (flowRecord.getId() == record.getId()) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void save(FlowRecord record) {
                List<FlowRecord> recordList = cache.getOrDefault(record.getProcessId(), new ArrayList<>());
                if (hasRecord(record, recordList)) {
                    List<FlowRecord> newRecordList = new ArrayList<>();
                    for (FlowRecord flowRecord : recordList) {
                        if (flowRecord.getId() == record.getId()) {
                            flowRecord = record;
                        }
                        newRecordList.add(flowRecord);
                    }
                    cache.put(record.getWorkId(), newRecordList);
                    records.put(record.getId(), record);
                } else {
                    recordList.add(record);
                    cache.put(record.getProcessId(), recordList);
                    records.put(record.getId(), record);
                }
            }

            @Override
            public List<FlowRecord> findToDoList(long processId, IFlowUser userId) {
                return cache.getOrDefault(processId, List.of()).stream().filter(record -> {
                    if (record.getState() == FlowState.WAIT) {
                        return record.getNode().matchUser(userId);
                    }
                    return false;
                }).toList();
            }

            @Override
            public FlowRecord get(long recordId) {
                return records.get(recordId);
            }

            @Override
            public List<FlowRecord> findAll(long processId, long nodeId) {
                List<FlowRecord> recordList = cache.getOrDefault(processId, List.of());
                return recordList.stream().filter(record -> record.getNode().getId() == nodeId).toList();
            }


            @Override
            public List<FlowRecord> findProcessList(long processId, IFlowUser flowUser) {
                List<FlowRecord> recordList = cache.getOrDefault(processId, List.of());
                return recordList.stream().filter(record -> {
                    if (record.getState() != FlowState.WAIT) {
                        return record.containsUser(flowUser);
                    }
                    return false;
                }).toList();
            }

            @Override
            public void delete(FlowRecord record) {
                List<FlowRecord> recordList = cache.getOrDefault(record.getProcessId(), List.of());
                List<FlowRecord> newRecordList = new ArrayList<>();
                for (FlowRecord flowRecord : recordList) {
                    if (flowRecord.getId() != record.getId()) {
                        newRecordList.add(flowRecord);
                    }
                }
                cache.put(record.getProcessId(), newRecordList);
                records.remove(record.getId());
            }

            @Override
            public List<FlowRecord> findAll(long processId) {
                return cache.getOrDefault(processId, List.of());
            }
        };
    }

    @Bean
    public FlowService flowService(FlowWorkRepository flowWorkRepository, FlowRecordRepository flowRecordRepository) {
        return new FlowService(flowWorkRepository, flowRecordRepository);
    }


    @Bean
    @ConditionalOnMissingBean
    public FlowIdGeneratorRegister flowIdGeneratorRegister() {
        SnowflakeIDGenerator snowflakeIDGenerator = new SnowflakeIDGenerator(1);
        return new FlowIdGeneratorRegister(snowflakeIDGenerator::getId);
    }


    @Bean
    @ConditionalOnMissingBean
    public FlowGateway flowGateway() {
        return new FlowGateway() {

            @Override
            public <T extends IFlowUser> T getUser(long userId, Class<T> clazz) {
                return null;
            }

            @Override
            public <T extends IBind> T getBind(long bindId, Class<T> clazz) {
                return null;
            }
        };
    }

    @Bean
    public FlowGatewayContextRegister flowGatewayContextRegister(FlowGateway flowGateway) {
        return new FlowGatewayContextRegister(flowGateway);
    }
}
