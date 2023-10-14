package ru.fedbon.validator;

public interface NumberRangeValidator {

    boolean validate(int value, int maxValue);

    String errorMessage();
}
