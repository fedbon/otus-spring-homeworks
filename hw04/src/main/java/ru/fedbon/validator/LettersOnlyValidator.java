package ru.fedbon.validator;

public interface LettersOnlyValidator {

    boolean validate(String value);

    String errorMessage();
}
