package com.codingapi.flow.gennerate;

public interface IdGenerator {

    long nextRecordId();

    long nextWorkId();

    long nextProcessId();

    long nextNodeId();

}
