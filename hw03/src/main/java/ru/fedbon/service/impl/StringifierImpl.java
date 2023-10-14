package ru.fedbon.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fedbon.domain.Question;
import ru.fedbon.domain.Result;
import ru.fedbon.service.LocalizationMessageService;
import ru.fedbon.service.Stringifier;


@Component
@RequiredArgsConstructor
public class StringifierImpl implements Stringifier {

    private final LocalizationMessageService messageService;

    @Override
    public String stringifyQuestion(Question question) {
        var builder = new StringBuilder(question.getText() + "\n");
        int optionNumber = 1;
        for (var answer : question.getOptions()) {
            builder
                    .append("   ")
                    .append(optionNumber++)
                    .append(". ")
                    .append(answer.getText())
                    .append("\n");
        }
        return builder.toString();
    }

    @Override
    public String stringifyResult(Result result, int scoreToPass) {
        var userFirstName = result.getUser().getFirstName();
        var userLastName = result.getUser().getLastName();
        var correctAnswers = result.getCorrectAnswers();
        var total = result.getTotal();
        return String.format(messageService.getLocalizedMessage("test.result.pattern"), userFirstName,
                userLastName, total, correctAnswers, scoreToPass, determineResult(correctAnswers, scoreToPass));
    }

    private String determineResult(int correctAnswers, int scoreToPass) {
        return correctAnswers >= scoreToPass ?
                messageService.getLocalizedMessage("message.test.passed") :
                messageService.getLocalizedMessage("message.test.failed");
    }
}

