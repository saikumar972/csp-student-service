package com.esrx.student.utility;

public class RequestTimeoutException extends RuntimeException {
    public RequestTimeoutException(String message) {
        super(message);
    }
}