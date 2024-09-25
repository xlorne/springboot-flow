package com.codingapi.flow.user;

import com.codingapi.flow.domain.FlowRecord;
import com.codingapi.flow.operator.IFlowOperator;
import lombok.Getter;
import lombok.Setter;

@Getter
public class User implements IFlowOperator {

    private final long id;
    private final String name;

    @Setter
    private String role;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
        this.role = "user";
    }

    @Override
    public boolean matcher(FlowRecord context) {
        return true;
    }
}
