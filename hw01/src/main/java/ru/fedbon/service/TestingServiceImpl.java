package ru.fedbon.service;

import lombok.AllArgsConstructor;
import ru.fedbon.domain.Question;
import ru.fedbon.dao.QuestionDao;
import ru.fedbon.service.api.TestingService;
import ru.fedbon.utils.Stringifier;


@AllArgsConstructor
public class TestingServiceImpl implements TestingService {

    private final QuestionDao csvQuestionDao;

    private final Stringifier<Question> stringifier;

    @Override
    public void executeTesting() {
        var questions = csvQuestionDao.getAll();
        var strings = stringifier.stringify(questions);
        strings.forEach(System.out::println);
    }
}

