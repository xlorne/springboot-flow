package com.codingapi.flow.gennerate;

public class IdGeneratorRegister {

    public IdGeneratorRegister(IdGenerator idGenerator) {
        IdGeneratorContext.getInstance().register(idGenerator);
    }
}
