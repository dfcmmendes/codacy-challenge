package com.daniel.codacy.challenge.errorhandling;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String description;

    public ServiceException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public ServiceException(ErrorCode errorCode, String description) {
        this(errorCode, description, null);
    }

    public ServiceException(ErrorCode errorCode, String description, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.description = description;
    }



}
