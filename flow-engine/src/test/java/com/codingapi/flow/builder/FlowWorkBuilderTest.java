package com.codingapi.flow.builder;

import com.codingapi.flow.domain.FlowWork;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowWorkBuilderTest {

    @Test
    void builder() {
        FlowWork flowWork =  FlowWorkBuilder.Builder().build();
        assertNotNull(flowWork);
        assertTrue(flowWork.isEnable());
        assertFalse(flowWork.isLock());
    }
}
