package ru.fedbon.services;

import lombok.AllArgsConstructor;
import ru.fedbon.domain.Answer;
import ru.fedbon.domain.Question;
import ru.fedbon.services.api.Parser;
import ru.fedbon.services.api.QuestionService;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final Parser csvParser;

    @Override
    public List<Question> getAllQuestions() {
        return csvParser.parse().stream().map(this::question).toList();
    }

    private Question question(List<String> list) {
        if (list.size() < 3) {
            throw new InvalidParameterException("Not enough strings to create instance of Question");
        }
        var answer = new Answer(list.get(1), true);
        var options = new ArrayList<>(list.subList(2, list.size()).stream().
                map(str -> new Answer(str, false))
                .toList());
        options.add(answer);
        Collections.shuffle(options);
        return new Question(list.get(0),options);
    }
}
