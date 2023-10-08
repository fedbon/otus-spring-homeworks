package ru.fedbon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fedbon.domain.Result;
import ru.fedbon.service.api.IOService;
import ru.fedbon.service.api.QuestionService;
import ru.fedbon.service.api.TestingService;
import ru.fedbon.service.api.UserService;
import ru.fedbon.utils.Message;

@Service
public class TestingServiceImpl implements TestingService {

    private final IOService ioService;

    private final QuestionService questionService;

    private final QuestionStringifierService questionStringifierService;

    private final ResultStringifierService resultStringifierService;

    private final UserService userService;

    private final int scoreToPass;

    public TestingServiceImpl(IOService ioService, QuestionService questionService,
                              QuestionStringifierService questionStringifierService,
                              ResultStringifierService resultStringifierService, UserService userService,
                              @Value("${score.to.pass}")int scoreToPass) {
        this.ioService = ioService;
        this.questionService = questionService;
        this.questionStringifierService = questionStringifierService;
        this.resultStringifierService = resultStringifierService;
        this.userService = userService;
        this.scoreToPass = scoreToPass;
    }

    @Override
    public void executeTesting() {
        ioService.output(Message.MSG_HEADER);
        ioService.output(Message.HEADER_DELIMITER);

        var user = userService.getUser();
        var result = new Result(user);

        var questions = questionService.getAllQuestions();
        questions.forEach(question -> {
            var option = ioService.readInt(questionStringifierService.stringify(question) +
                    Message.MSG_ENTER_ANSWER, question.getOptions().size()) - 1;
            var isCorrect = question.getOptions().get(option).isCorrect();
            result.incrementCorrectAnswers(isCorrect);
            ioService.output(isCorrect ? Message.MSG_RIGHT_ANSWER : Message.MSG_WRONG_ANSWER);
        });
        ioService.output(resultStringifierService.stringify(result, scoreToPass));
    }
}

