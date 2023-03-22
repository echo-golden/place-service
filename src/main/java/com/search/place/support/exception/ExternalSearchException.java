package com.search.place.support.exception;

import com.search.place.application.ResponseCode;
import lombok.Getter;

public class ExternalSearchException extends RuntimeException {
    @Getter
    private ResponseCode responseCode;

    public ExternalSearchException() {
        super();
    }

    public ExternalSearchException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
