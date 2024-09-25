package com.codingapi.flow.user;

import com.codingapi.flow.operator.IFlowOperator;
import lombok.Getter;

@Getter
public class User implements IFlowOperator {

    private final long id;
    private final String name;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
