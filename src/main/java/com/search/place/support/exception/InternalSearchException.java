package com.search.place.support.exception;

import com.search.place.application.ResponseCode;
import lombok.Getter;

public class InternalSearchException extends RuntimeException {
    @Getter
    private ResponseCode responseCode;

    public InternalSearchException() {
        super();
    }

    public InternalSearchException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
