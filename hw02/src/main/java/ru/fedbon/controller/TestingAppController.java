package ru.fedbon.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fedbon.controller.api.Controller;
import ru.fedbon.service.TestingServiceImpl;


@Component
@RequiredArgsConstructor
public class TestingAppController implements Controller {

    private final TestingServiceImpl testingServiceImpl;

    @Override
    public void executeTesting() {
        testingServiceImpl.displayHeader();

        var result = testingServiceImpl.getResult(testingServiceImpl.registryUser());

        testingServiceImpl.displayResult(result);
    }
}

