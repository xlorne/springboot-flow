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

    private long nextRecordId;

    private long nextNodeId;

    private long nextProcessId;

    public FlowNodeSeq(int id) {
        this.id = id;
        this.nextRecordId = 0;
        this.nextNodeId = 0;
        this.nextProcessId = 0;
    }

    public void addNextRecordId() {
        this.nextRecordId++;
    }
    public void addNextNodeId() {
        this.nextNodeId++;
    }

    public void addNextProcessId() {
        this.nextProcessId++;
    }

}
