package com.codingapi.flow.creator;

import com.codingapi.flow.domain.FlowRecord;

public class DefaultTitleCreator implements ITitleCreator {

    @Override
    public String createTitle(FlowRecord record) {
        if (record.getNode().isOver()) {
            return String.format("%s-%s",
                    record.getNode().getFlowWork().getTitle(),
                    record.getNode().getName());
        }
        return String.format("%s-%s-%s",
                record.getNode().getFlowWork().getTitle(),
                record.getNode().getName(),
                record.getOperatorUser().getName());
    }
}
