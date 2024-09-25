package com.codingapi.flow.creator;

import com.codingapi.flow.domain.FlowRecord;

public class DefaultTitleCreator implements ITitleCreator {

    @Override
    public String createTitle(FlowRecord record) {
        return String.format("%s-%s-%s",
                record.getNode().getFlowWork().getTitle(),
                record.getNode().getName(),
                record.getOperatorUser().getName());
    }
}
