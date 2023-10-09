package ru.fedbon.service;

import org.springframework.stereotype.Service;
import ru.fedbon.domain.Result;
import ru.fedbon.domain.User;
import ru.fedbon.service.api.IOService;
import ru.fedbon.service.api.TestingService;
import ru.fedbon.service.api.QuestionService;
import ru.fedbon.service.api.UserService;
import ru.fedbon.utils.Message;

@Service
public class TestingServiceImpl implements TestingService {

    private final IOService ioService;

    private final UserService userService;

    private final QuestionService questionService;

    private final QuestionStringifier questionStringifier;

    private final ResultStringifier resultStringifier;


    public TestingServiceImpl(IOService ioService, UserService userService, QuestionService questionService,
                              QuestionStringifier questionStringifier, ResultStringifier resultStringifier) {
        this.ioService = ioService;
        this.userService = userService;
        this.questionService = questionService;
        this.questionStringifier = questionStringifier;
        this.resultStringifier = resultStringifier;
    }

    public void displayHeader() {
        ioService.output(Message.MSG_HEADER);
        ioService.output(Message.HEADER_DELIMITER);
    }

    public User registryUser() {
        return userService.getUser();
    }

    public Result getResult(User user) {
        var result = new Result(user);

        var questions = questionService.getAllQuestions();
        questions.forEach(question -> {
            var option = ioService.readInt(questionStringifier.stringify(question) +
                    Message.MSG_ENTER_ANSWER, question.getOptions().size()) - 1;
            var isCorrect = question.getOptions().get(option).isCorrect();
            result.incrementCorrectAnswers(isCorrect);
            ioService.output(isCorrect ? Message.MSG_RIGHT_ANSWER : Message.MSG_WRONG_ANSWER);
        });

        return result;
    }

    public void displayResult(Result result) {
        ioService.output(resultStringifier.stringify(result, resultStringifier.getScoreToPass()));
    }
}
