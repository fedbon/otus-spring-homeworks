package ru.fedbon.service.api;

import ru.fedbon.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestions();
}
