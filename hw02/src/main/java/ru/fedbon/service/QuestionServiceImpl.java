package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.dao.QuestionDao;
import ru.fedbon.domain.Question;
import ru.fedbon.service.api.QuestionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;

    @Override
    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }
}
