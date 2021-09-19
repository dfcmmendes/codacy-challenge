package com.daniel.codacy.challenge.errorhandling;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.HttpURLConnection;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCodeImpl implements ErrorCode{

    NOT_FOUND_ERROR("0001", "The repository is not valid. Verify is the ownwer or repo name is correct " +
                            "and the repo is public", HttpURLConnection.HTTP_NOT_FOUND),
    BAD_REQUEST("0002", "Invalid fields in the request", HttpURLConnection.HTTP_BAD_REQUEST),
    INTERNAL_SERVER_ERROR("0003", "Something weird happened!", HttpURLConnection.HTTP_INTERNAL_ERROR);

    private final String code;
    private final String message;
    private final int httpStatusCode;
}
