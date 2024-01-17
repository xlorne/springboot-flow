package com.codingapi.flow.gennerate;

import lombok.Getter;

public class IdGeneratorContext {

    @Getter
    private final static IdGeneratorContext instance = new IdGeneratorContext();

    private IdGeneratorContext() {
    }

    private IdGenerator idGenerator;

    void register(IdGenerator idGenerator){
        this.idGenerator = idGenerator;
    }

    public long nextRecordId() {
       return idGenerator.nextRecordId();
    }

    public long nextNodeId(){
        return idGenerator.nextNodeId();
    }

    public long nextProcessId(){
        return idGenerator.nextProcessId();
    }
}
