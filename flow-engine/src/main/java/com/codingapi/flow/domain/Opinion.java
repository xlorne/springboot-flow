package com.codingapi.flow.domain;

import lombok.Getter;

@Getter
public class Opinion {

    private final String opinion;
    private final boolean pass;

    public Opinion(String opinion, boolean pass) {
        this.opinion = opinion;
        this.pass = pass;
    }

    public Opinion(String opinion) {
        this.opinion = opinion;
        this.pass = true;
    }

    public static Opinion pass(String opinion) {
        return new Opinion(opinion, true);
    }
}
