package ru.fedbon;

import lombok.RequiredArgsConstructor;
import ru.fedbon.domain.Question;
import ru.fedbon.services.api.Printer;
import ru.fedbon.services.api.QuestionService;
import ru.fedbon.services.api.Stringifier;

import java.util.List;

@RequiredArgsConstructor
public class App {

    private final QuestionService questionService;

    private final Stringifier<Question> stringifier;

    private final Printer printer;

    public void run() {
        List<String> strings = stringifier.stringify(questionService.getAllQuestions());
        printer.print(strings);
    }

}
