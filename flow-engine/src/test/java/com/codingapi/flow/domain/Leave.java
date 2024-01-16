package com.codingapi.flow.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Leave implements IBind {

    private int id;
    private String reason;
    private User user;
    private float days;
    private long createTime;

    public Leave(int id, String reason, User user, float days) {
        this.id = id;
        this.reason = reason;
        this.user = user;
        this.days = days;
        this.createTime = System.currentTimeMillis();
    }

    @Override
    public long getId() {
        return id;
    }
}
