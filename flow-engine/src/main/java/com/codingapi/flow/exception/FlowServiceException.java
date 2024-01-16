package com.codingapi.flow.exception;

import com.codingapi.springboot.framework.exception.LocaleMessageException;

public class FlowServiceException extends LocaleMessageException {

    public FlowServiceException(String errCode, String errMessage) {
        super(errCode, errMessage);
    }

}
