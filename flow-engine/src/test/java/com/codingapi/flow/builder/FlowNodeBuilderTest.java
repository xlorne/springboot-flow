package com.codingapi.flow.builder;

import com.codingapi.flow.domain.FlowNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FlowNodeBuilderTest {

    @Test
    void Builder(){
        FlowNode flowNode =  FlowNodeBuilder.Builder().build();
        assertNotNull(flowNode);
    }

}
