package ru.fedbon.stringifier;

import org.springframework.stereotype.Component;
import ru.fedbon.domain.Question;


@Component
public class QuestionStringifierImpl implements QuestionStringifier {

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


