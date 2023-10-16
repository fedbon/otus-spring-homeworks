package ru.fedbon.exception;

public class AppException extends RuntimeException {
    public AppException(Exception e) {
        super(e);
    }
}

