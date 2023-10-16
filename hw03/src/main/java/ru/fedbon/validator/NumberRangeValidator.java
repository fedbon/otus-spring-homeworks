package ru.fedbon.validator;

import org.springframework.stereotype.Component;

@Component
public class NumberRangeValidator implements Validator {

    @Override
    public boolean validate(int value, int maxValue) {
        return value < 1 || value > maxValue;
    }

    @Override
    public String errorMessage() {
        return "error.message.invalid.number.input";
    }
}
