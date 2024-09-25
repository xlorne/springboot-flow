package com.codingapi.flow.creator;

import com.codingapi.flow.domain.FlowRecord;

public class DefaultTitleCreator implements ITitleCreator{

    @Override
    public String createTitle(FlowRecord record) {
        return record.getTitle()+"-"+record.getNode().getName()+record.getOperatorUser().getName();
    }
}
