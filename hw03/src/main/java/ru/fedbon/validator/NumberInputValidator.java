package ru.fedbon.validator;

import org.springframework.stereotype.Component;

@Component
public class NumberInputValidator implements Validator {

    @Override
    public boolean validate(int value, int maxValue) {
        return value < 1 || value > maxValue;
    }

    @Override
    public String errorMessage() {
        return "Invalid input! Please enter a number between 1 and ";
    }
}
