package com.codingapi.flow.gennerate;

public class FlowIdGeneratorRegister {

    public FlowIdGeneratorRegister(IdGenerator idGenerator) {
        IdGeneratorContext.getInstance().register(idGenerator);
    }
}
