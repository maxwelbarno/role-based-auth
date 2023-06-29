package com.tuts.auth.exceptions;

public class EmptyInputException extends RuntimeException {
    public EmptyInputException() {
    }

    public EmptyInputException(String message) {
        super(message);
    }

    public EmptyInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
