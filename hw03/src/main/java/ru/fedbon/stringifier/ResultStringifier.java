package ru.fedbon.stringifier;


import ru.fedbon.domain.Result;


public interface ResultStringifier {
    String stringify(Result result, int scoreToPass);

}
