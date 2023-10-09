package com.tuts.auth.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String user, Integer id) {
        super("Could not find " + user + " with Id " + id);
    }

    public UserNotFoundException(String username) {
        super("Could not find user with username " + username);
    }
}
