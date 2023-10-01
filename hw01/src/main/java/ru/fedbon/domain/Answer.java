package ru.fedbon.domain;

import lombok.Data;

@Data
public class Answer {
    private final String answerText;

    private final boolean corrected;
}
