package ru.fedbon.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class LettersOnlyValidator implements Validator {

    private static final Pattern LETTERS_ONLY_PATTERN = Pattern.compile("\\p{L}+");

    @Override
    public boolean validate(String value) {
        return !LETTERS_ONLY_PATTERN.matcher(value).matches();
    }

    @Override
    public String errorMessage() {
        return "error.message.invalid.string.input";
    }
}
