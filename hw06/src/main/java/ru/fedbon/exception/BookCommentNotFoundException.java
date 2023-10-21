package ru.fedbon.exception;

public class BookCommentNotFoundException extends RuntimeException {

    public BookCommentNotFoundException(String message) {
        super(message);
    }
}
