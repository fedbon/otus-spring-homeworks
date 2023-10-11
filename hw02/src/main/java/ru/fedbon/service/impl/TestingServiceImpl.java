package ru.fedbon.service.impl;

import org.springframework.stereotype.Service;
import ru.fedbon.domain.Result;
import ru.fedbon.service.IOService;
import ru.fedbon.service.QuestionService;
import ru.fedbon.service.TestingService;
import ru.fedbon.utils.Message;

@Service
public class TestingServiceImpl implements TestingService {

    private final IOService ioService;

    private final QuestionService questionService;

    private final QuestionStringifier questionStringifier;


    public TestingServiceImpl(IOService ioService, QuestionService questionService,
                              QuestionStringifier questionStringifier) {
        this.ioService = ioService;
        this.questionService = questionService;
        this.questionStringifier = questionStringifier;
    }

    @Override
    public void displayHeader() {
        ioService.output(Message.MSG_HEADER);
        ioService.output(Message.HEADER_DELIMITER);
    }

    @Override
    public void processTesting(Result result) {
        var questions = questionService.getAllQuestions();
        questions.forEach(question -> {
            var option = ioService.readInt(questionStringifier.stringify(question) +
                    Message.MSG_ENTER_ANSWER, question.getOptions().size()) - 1;
            var isCorrect = question.getOptions().get(option).isCorrect();
            result.incrementCorrectAnswers(isCorrect);
            ioService.output(isCorrect ? Message.MSG_RIGHT_ANSWER : Message.MSG_WRONG_ANSWER);
        });
    }
}
