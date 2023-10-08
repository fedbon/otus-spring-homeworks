package ru.fedbon.service;

import org.springframework.stereotype.Service;
import ru.fedbon.domain.Result;
import ru.fedbon.service.api.Stringifier;
import ru.fedbon.utils.Message;

@Service
public class ResultStringifierService implements Stringifier {

    @Override
    public String stringify(Result result, int scoreToPass) {
        var userFirstName = result.getUser().getFirstName();
        var userLastName = result.getUser().getLastName();
        var rightAnswers = result.getCorrectAnswers();
        var total = result.getTotal();
        return String.format(Message.TEST_RESULT_PATTERN, userFirstName, userLastName, total,
                rightAnswers, scoreToPass, determineResult(rightAnswers, scoreToPass));
    }

    private String determineResult(int correctAnswers, int scoreToPass) {
        return correctAnswers >= scoreToPass ? Message.MSG_TEST_PASSED : Message.MSG_TEST_FAILED;
    }
}
