package ru.fedbon.domain;

import lombok.Data;

import java.util.List;


@Data
public class Question {
    private final String text;

    private final List<Answer> options;
}
