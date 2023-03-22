package com.search.place.support;

import com.search.place.application.ResponseCode;
import com.search.place.application.search.response.PlaceSearchResponse;
import com.search.place.support.exception.ExternalSearchException;
import com.search.place.support.exception.InternalSearchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ExternalSearchException.class)
    public ResponseEntity<?> handleExternalSearchException() {
        return new ResponseEntity<>(PlaceSearchResponse.error(ResponseCode.EXTERNAL_API_5xx_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InternalSearchException.class, InterruptedException.class})
    public ResponseEntity<?> handleInternalSearchException() {
        return new ResponseEntity<>(PlaceSearchResponse.error(ResponseCode.INTERNAL_SERVICE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
