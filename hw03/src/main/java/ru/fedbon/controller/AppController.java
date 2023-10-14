package ru.fedbon.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fedbon.domain.Result;
import ru.fedbon.service.ResultService;
import ru.fedbon.service.TestingService;
import ru.fedbon.service.UserService;



@Component
@RequiredArgsConstructor
public class AppController implements Controller {

    private final TestingService testingService;

    private final UserService userService;

    private final ResultService resultService;

    @Override
    public void executeTesting() {
        testingService.displayHeader();

        var result = new Result(userService.getUser());

        testingService.processTesting(result);

        resultService.displayResult(result);
    }
}

