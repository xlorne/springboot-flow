package com.codingapi.flow.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class FlowNodeSeq {

    @Id
    private int id;

    private long nextProcessId;

    public FlowNodeSeq(int id) {
        this.id = id;
        this.nextProcessId = 0;
    }

    public void addNextProcessId() {
        this.nextProcessId++;
    }

}
