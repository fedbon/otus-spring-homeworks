package ru.fedbon.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fedbon.domain.Result;
import ru.fedbon.service.api.Stringifier;
import ru.fedbon.utils.Message;

@Service
@Getter
public class ResultStringifierService implements Stringifier {

    private final int scoreToPass;

    public ResultStringifierService(@Value("${score.to.pass}") int scoreToPass) {
        this.scoreToPass = scoreToPass;
    }

    @Override
    public String stringify(Result result, int scoreToPass) {
        var userFirstName = result.getUser().getFirstName();
        var userLastName = result.getUser().getLastName();
        var rightAnswers = result.getCorrectAnswers();
        var total = result.getTotal();
        return String.format(Message.TEST_RESULT_PATTERN, userFirstName, userLastName, total,
                rightAnswers, this.scoreToPass, determineResult(rightAnswers, this.scoreToPass));
    }

    private String determineResult(int correctAnswers, int scoreToPass) {
        return correctAnswers >= scoreToPass ? Message.MSG_TEST_PASSED : Message.MSG_TEST_FAILED;
    }
}

