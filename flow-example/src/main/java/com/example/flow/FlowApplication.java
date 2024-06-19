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
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }
}
