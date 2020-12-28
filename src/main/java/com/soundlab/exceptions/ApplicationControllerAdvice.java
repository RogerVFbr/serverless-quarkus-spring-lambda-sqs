package com.soundlab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import software.amazon.awssdk.services.sqs.model.SqsException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(SqsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleAwsResourceException(SqsException ex) {
        return new ApiErrors().withError(ex.getMessage());
    }

    @ExceptionHandler(NoMessagesInQueueException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiErrors handleNoMessagesInQueueException(NoMessagesInQueueException ex) {
        return new ApiErrors()
            .withError(ex.getMessage())
            .withError(ex.getLocalizedMessage());
    }
}
