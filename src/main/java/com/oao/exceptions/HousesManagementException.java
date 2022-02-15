package com.oao.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when validation failed or other cases
 */
public class HousesManagementException extends RuntimeException {
    private final String displayMessage;
    private final HttpStatus httpStatus;

    public HousesManagementException(String message) {
        super(message);
        displayMessage = message;
        httpStatus = HttpStatus.FORBIDDEN;
    }

    public HousesManagementException(String message,
                                     HttpStatus httpStatus) {
        super(message);
        displayMessage = message;
        this.httpStatus = httpStatus;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
