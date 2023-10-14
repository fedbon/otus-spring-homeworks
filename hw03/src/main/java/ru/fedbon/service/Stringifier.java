package ru.fedbon.service;


import ru.fedbon.domain.Question;
import ru.fedbon.domain.Result;


public interface Stringifier {

    String stringifyQuestion(Question question);

    String stringifyResult(Result result, int scoreToPass);
}
