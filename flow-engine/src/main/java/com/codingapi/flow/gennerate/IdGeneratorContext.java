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

    public long nextId() {
       return idGenerator.nextId();
    }
}
