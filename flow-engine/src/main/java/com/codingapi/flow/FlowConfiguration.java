package com.codingapi.flow;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.bind.IBind;
import com.codingapi.flow.em.FlowState;
import com.codingapi.flow.user.IFlowUser;
import com.codingapi.flow.gateway.FlowGateway;
import com.codingapi.flow.gateway.FlowGatewayContextRegister;
import com.codingapi.flow.gennerate.FlowIdGeneratorRegister;
import com.codingapi.flow.gennerate.IdGenerator;
import com.codingapi.flow.gennerate.SnowflakeIDGenerator;
import com.codingapi.flow.repository.FlowRecordQuery;
import com.codingapi.flow.repository.FlowRecordRepository;
import com.codingapi.flow.repository.FlowWorkRepository;
import com.codingapi.flow.service.FlowQuery;
import com.codingapi.flow.service.FlowService;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;

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

    private static class FlowRecordCache {
        private final Map<Long, List<FlowRecord>> cache;

        @Getter
        private final static FlowRecordCache instance = new FlowRecordCache();

        private FlowRecordCache() {
            this.cache = new HashMap<>();
        }

        public void put(long processId, List<FlowRecord> recordList) {
            cache.put(processId, recordList);
        }

        public List<FlowRecord> get(long processId, List<FlowRecord> defaultList) {
            return cache.getOrDefault(processId, defaultList);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public FlowRecordQuery flowRecordQuery() {
        return new FlowRecordQuery() {
            @Override
            public List<FlowRecord> findToDoList(long processId, IFlowUser flowUser) {
                return FlowRecordCache.getInstance().get(processId, List.of()).stream().filter(record -> {
                    if (record.getState() == FlowState.WAIT) {
                        if(record.getUsers()!=null && !record.getUsers().isEmpty()){
                            return record.containsUser(flowUser);
                        }
                        return record.getNode().matchUser(flowUser);
                    }
                    return false;
                }).toList();
            }

            @Override
            public List<FlowRecord> findProcessList(long processId, IFlowUser flowUser) {
                List<FlowRecord> recordList = FlowRecordCache.getInstance().get(processId, List.of());
                return recordList.stream().filter(record -> {
                    if (record.getState() != FlowState.WAIT) {
                        return record.containsUser(flowUser);
                    }
                    return false;
                }).toList();
            }

            @Override
            public Page<FlowRecord> findToDoPage(PageRequest request, IFlowUser currentUser) {
                // 单元测试 不允许访问该功能
                throw new RuntimeException("not support");
            }

            @Override
            public Page<FlowRecord> findProcessPage(PageRequest request, IFlowUser currentUser) {
                // 单元测试 不允许访问该功能
                throw new RuntimeException("not support");
            }

            @Override
            public List<FlowRecord> findByProcessId(long processId) {
                // 单元测试 不允许访问该功能
                throw new RuntimeException("not support");
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public FlowRecordRepository flowRecordRepository() {
        return new FlowRecordRepository() {
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
                List<FlowRecord> recordList = FlowRecordCache.getInstance().get(record.getProcessId(), new ArrayList<>());
                if (hasRecord(record, recordList)) {
                    List<FlowRecord> newRecordList = new ArrayList<>();
                    for (FlowRecord flowRecord : recordList) {
                        if (flowRecord.getId() == record.getId()) {
                            flowRecord = record;
                        }
                        newRecordList.add(flowRecord);
                    }
                    FlowRecordCache.getInstance().put(record.getWorkId(), newRecordList);
                    records.put(record.getId(), record);
                } else {
                    recordList.add(record);
                    FlowRecordCache.getInstance().put(record.getProcessId(), recordList);
                    records.put(record.getId(), record);
                }
            }


            @Override
            public FlowRecord get(long recordId) {
                return records.get(recordId);
            }

            @Override
            public List<FlowRecord> findByProcessIdOrderByCreateTimeDesc(long processId, long nodeId, int top) {
                List<FlowRecord> recordList = FlowRecordCache.getInstance().get(processId, List.of());
                return recordList.stream()
                        .filter(record -> record.getNode().getId() == nodeId)
                        .sorted(Comparator.comparing(FlowRecord::getCreateTime).reversed())
                        .limit(top)
                        .toList();
            }


            @Override
            public void delete(FlowRecord record) {
                List<FlowRecord> recordList = FlowRecordCache.getInstance().get(record.getProcessId(), List.of());
                List<FlowRecord> newRecordList = new ArrayList<>();
                for (FlowRecord flowRecord : recordList) {
                    if (flowRecord.getId() != record.getId()) {
                        newRecordList.add(flowRecord);
                    }
                }
                FlowRecordCache.getInstance().put(record.getProcessId(), newRecordList);
                records.remove(record.getId());
            }

            @Override
            public List<FlowRecord> findByProcessIdOrderByCreateTimeDesc(long processId) {
                return FlowRecordCache.getInstance().get(processId, List.of());
            }
        };
    }

    @Bean
    public FlowService flowService(FlowWorkRepository flowWorkRepository, FlowRecordRepository flowRecordRepository) {
        return new FlowService(flowWorkRepository, flowRecordRepository);
    }

    @Bean
    public FlowQuery flowQuery(FlowRecordQuery flowRecordQuery) {
        return new FlowQuery(flowRecordQuery);
    }


    @Bean
    @ConditionalOnMissingBean
    public FlowIdGeneratorRegister flowIdGeneratorRegister() {
        SnowflakeIDGenerator snowflakeIDGenerator = new SnowflakeIDGenerator(0);
        return new FlowIdGeneratorRegister(new IdGenerator() {
            @Override
            public long nextRecordId() {
                return snowflakeIDGenerator.getId();
            }

            @Override
            public long nextProcessId() {
                return snowflakeIDGenerator.getId();
            }

            @Override
            public long nextNodeId() {
                return snowflakeIDGenerator.getId();
            }

            @Override
            public long nextWorkId() {
                return snowflakeIDGenerator.getId();
            }
        });
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
