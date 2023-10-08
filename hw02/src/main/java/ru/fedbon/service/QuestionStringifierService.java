package ru.fedbon.service;


import org.springframework.stereotype.Service;
import ru.fedbon.domain.Question;
import ru.fedbon.service.api.Stringifier;


@Service
public class QuestionStringifierService implements Stringifier {

    @Override
    public String stringify(Question question) {
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
}

