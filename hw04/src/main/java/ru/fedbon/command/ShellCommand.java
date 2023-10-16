package ru.fedbon.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.controller.AppController;
import ru.fedbon.domain.Result;
import ru.fedbon.service.ResultService;
import ru.fedbon.service.TestingService;
import ru.fedbon.service.UserService;


@ShellComponent
@RequiredArgsConstructor
public class ShellCommand {

    private final AppController appController;

    private final TestingService testingService;

    private final ResultService resultService;

    private final UserService userService;

    @ShellMethod(value = "startTesting", key = {"start-testing"})
    public void startTesting() {
        appController.executeTesting();
    }

    @ShellMethod(value = "executeDisplayHeader", key = {"display-header"})
    public void executeDisplayHeader() {
        testingService.displayHeader();
    }

    @ShellMethod(value = "executeProcessTesting", key = {"process-testing"})
    public void executeProcessTesting() {
        testingService.processTesting(new Result(userService.getUser()));
    }

    @ShellMethod(value = "executeDisplayResult", key = {"display-result"})
    public void executeDisplayResult() {
        resultService.displayResult((new Result(userService.getUser())));
    }

}
