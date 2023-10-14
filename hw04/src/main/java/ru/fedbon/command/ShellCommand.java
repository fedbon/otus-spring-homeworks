package ru.fedbon.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.AppRunner;


@ShellComponent
@RequiredArgsConstructor
public class ShellCommand {

    private final AppRunner appRunner;


    @ShellMethod(value = "Start testing", key = {"start"})
    public void startTesting() {
        appRunner.run();
    }

}
