package ru.fedbon.service.api;


import ru.fedbon.domain.Question;
import ru.fedbon.domain.Result;


public interface Stringifier {

    default String stringify(Question question) {
        return null;
    }

    default String stringify(Result result, int scoreToPass) {
        return null;
    }
}
