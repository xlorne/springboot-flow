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

    private long nextId;

    public FlowNodeSeq(int id) {
        this.id = id;
        this.nextId = id;
    }

    public void addNextId() {
        this.nextId++;
    }
}
