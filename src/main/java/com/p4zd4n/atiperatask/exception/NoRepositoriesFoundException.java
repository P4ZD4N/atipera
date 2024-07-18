package com.p4zd4n.atiperatask.exception;

public class NoRepositoriesFoundException extends RuntimeException {

    public NoRepositoriesFoundException(String username) {
        super("No repositories found for user " + username);
    }
}
