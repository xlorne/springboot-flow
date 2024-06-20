package com.example.flow.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class LeaveCmd {

    @Setter
    @Getter
    @ToString
    public static class CreateRequest{
        private String reason;
        private int days;
        private long workId;
        private String username;

    }
}
