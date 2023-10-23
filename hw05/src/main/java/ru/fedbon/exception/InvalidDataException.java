package ru.fedbon.exception;

public class InvalidDataException extends IllegalArgumentException {

    public InvalidDataException(String message) {
        super(message);
    }
}
