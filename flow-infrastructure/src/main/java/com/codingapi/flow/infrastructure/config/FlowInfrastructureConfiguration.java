package com.codingapi.flow.infrastructure.config;

import com.codingapi.flow.infrastructure.context.FlowNodeContextRegister;
import com.codingapi.flow.infrastructure.gateway.impl.FlowProcessIdGeneratorGatewayImpl;
import com.codingapi.flow.infrastructure.generator.FlowNodeSeqGenerator;
import com.codingapi.flow.infrastructure.jpa.FlowNodeEntityRepository;
import com.codingapi.flow.infrastructure.jpa.FlowNodeSeqRepository;
import com.codingapi.flow.infrastructure.jpa.FlowRecordEntityRepository;
import com.codingapi.flow.infrastructure.jpa.FlowWorkEntityRepository;
import com.codingapi.flow.infrastructure.repository.impl.FlowRecordQueryImpl;
import com.codingapi.flow.infrastructure.repository.impl.FlowRecordRepositoryImpl;
import com.codingapi.flow.infrastructure.repository.impl.FlowWorkQueryImpl;
import com.codingapi.flow.infrastructure.repository.impl.FlowWorkRepositoryImpl;
import com.codingapi.flow.repository.FlowRecordQuery;
import com.codingapi.flow.repository.FlowRecordRepository;
import com.codingapi.flow.repository.FlowWorkQuery;
import com.codingapi.flow.repository.FlowWorkRepository;
import com.codingapi.flow.service.FlowQuery;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.codingapi.flow.infrastructure.entity"})
@EnableJpaRepositories(basePackages = {"com.codingapi.flow.infrastructure.jpa"})
public class FlowInfrastructureConfiguration {

    @Bean
    public FlowWorkRepository flowWorkRepository(FlowNodeEntityRepository flowNodeEntityRepository, FlowWorkEntityRepository flowWorkEntityRepository) {
        return new FlowWorkRepositoryImpl(flowWorkEntityRepository, flowNodeEntityRepository);
    }

    @Bean
    public FlowRecordRepository flowRecordRepository(FlowRecordEntityRepository flowRecordEntityRepository) {
        return new FlowRecordRepositoryImpl(flowRecordEntityRepository);
    }

    @Bean
    public FlowRecordQuery flowRecordQuery(FlowRecordEntityRepository flowRecordEntityRepository, FlowWorkEntityRepository flowWorkEntityRepository) {
        return new FlowRecordQueryImpl(flowRecordEntityRepository, flowWorkEntityRepository);
    }

    @Bean
    public FlowWorkQuery flowWorkQuery(FlowWorkEntityRepository flowWorkEntityRepository) {
        return new FlowWorkQueryImpl(flowWorkEntityRepository);
    }

    @Bean
    public FlowNodeContextRegister flowNodeContextRegister(FlowNodeEntityRepository flowNodeEntityRepository) {
        return new FlowNodeContextRegister(flowNodeEntityRepository);
    }

    @Bean
    public FlowNodeSeqGenerator flowNodeSeqGenerator(FlowNodeSeqRepository flowNodeSeqRepository) {
        return new FlowNodeSeqGenerator(flowNodeSeqRepository);
    }

    @Bean
    public FlowProcessIdGeneratorGatewayImpl flowProcessIdGeneratorGatewayImpl(FlowNodeSeqGenerator flowNodeSeqGenerator) {
        return new FlowProcessIdGeneratorGatewayImpl(flowNodeSeqGenerator);
    }

    @Bean
    public FlowQuery flowQuery(FlowRecordQuery flowRecordQuery) {
        return new FlowQuery(flowRecordQuery);
    }

}
