package com.codingapi.flow.user;

import com.codingapi.flow.operator.IFlowOperator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class User implements IFlowOperator {

    @Setter
    private long id;
    private final String name;

    @Setter
    private String role;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
        this.role = "user";
    }
}
