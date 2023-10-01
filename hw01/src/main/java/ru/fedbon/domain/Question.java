package ru.fedbon.domain;

import lombok.Data;

import java.util.List;


@Data
public class Question {
    private final String question;

    private final List<Answer> options;
}
