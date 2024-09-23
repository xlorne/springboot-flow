package com.codingapi.flow;

import com.codingapi.flow.gateway.FlowGateway;
import com.codingapi.flow.gateway.FlowGatewayContextRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowConfiguration {


    @Bean
    public FlowGatewayContextRegister flowGatewayContextRegister(FlowGateway flowGateway) {
        return new FlowGatewayContextRegister(flowGateway);
    }


}
