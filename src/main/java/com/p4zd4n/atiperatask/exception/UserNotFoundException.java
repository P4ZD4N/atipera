package com.p4zd4n.atiperatask.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("User " + username + " not found!");
    }
}
