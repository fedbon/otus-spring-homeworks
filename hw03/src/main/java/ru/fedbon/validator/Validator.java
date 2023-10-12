package ru.fedbon.validator;

public interface Validator {

    default boolean validate(int value, int maxValue) {
        return true;
    }

    default boolean validate(String value) {
        return false;
    }

    String errorMessage();
}
