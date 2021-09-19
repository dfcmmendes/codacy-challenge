package com.daniel.codacy.challenge.errorhandling;

public interface ErrorCode {
    String getCode();

    String getMessage();

    int getHttpStatusCode();
}
