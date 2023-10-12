package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fedbon.domain.Result;
import ru.fedbon.service.LocalizationMessageService;
import ru.fedbon.service.Stringifier;


@Component
@RequiredArgsConstructor
public class ResultStringifier implements Stringifier {

    private final LocalizationMessageService messageService;


    @Override
    public String stringify(Result result, int scoreToPass) {
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

