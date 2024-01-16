package com.codingapi.flow.exception;

import com.codingapi.springboot.framework.exception.LocaleMessageException;

public class FlowBuilderException extends LocaleMessageException {

    public FlowBuilderException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

}
