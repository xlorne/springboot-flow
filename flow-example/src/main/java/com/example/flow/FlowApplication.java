package com.example.flow;

import com.codingapi.flow.domain.FlowWork;
import com.codingapi.flow.gateway.FlowProcessIdGeneratorGateway;
import com.codingapi.flow.repository.FlowRecordRepository;
import com.codingapi.flow.repository.FlowWorkRepository;
import com.codingapi.flow.service.FlowService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.example.flow.domain"})
@EnableJpaRepositories(basePackages = {"com.example.flow.repository"})
@SpringBootApplication
public class FlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowApplication.class, args);
    }

    @Bean
    public FlowService flowService(FlowWorkRepository flowWorkRepository, FlowRecordRepository flowRecordRepository, FlowProcessIdGeneratorGateway flowProcessIdGeneratorGateway) {
        return new FlowService(flowWorkRepository, flowRecordRepository, flowProcessIdGeneratorGateway);
    }
}
