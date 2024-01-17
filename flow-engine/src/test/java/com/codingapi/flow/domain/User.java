package com.codingapi.flow.domain;

import com.codingapi.flow.user.IFlowUser;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User implements IFlowUser {

    private long id;
    private String name;
    private String role;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(long id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
