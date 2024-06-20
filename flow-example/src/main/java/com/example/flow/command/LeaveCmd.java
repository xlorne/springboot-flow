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

    @Setter
    @Getter
    @ToString
    public static class PassRequest{
        private String opinion;
        private long recordId;
        private String username;
    }

    @Setter
    @Getter
    @ToString
    public static class RejectRequest{
        private String opinion;
        private long recordId;
        private String username;
    }

    @Setter
    @Getter
    @ToString
    public static class RecallRequest{
        private String username;
        private long recordId;
    }

    @Setter
    @Getter
    @ToString
    public static class BackRequest{
        private String opinion;
        private long recordId;
        private String username;
    }
}
