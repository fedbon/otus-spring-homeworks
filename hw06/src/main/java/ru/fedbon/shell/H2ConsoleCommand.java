package ru.fedbon.shell;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.fedbon.exception.ConsoleNotStartedException;

import java.sql.SQLException;
import java.util.Arrays;

import static java.lang.String.format;

@ShellComponent
public class H2ConsoleCommand {

    @ShellMethod(key = {"start-h2-web-console", "start-console"},
            value = "Запускает веб-консоль H2")
    public String startH2Console(@ShellOption(defaultValue = "-browser") String[] args) {
        try {
            Console.main(args);
        } catch (SQLException e) {
            throw new ConsoleNotStartedException("Произошла ошибка при запуске консоли", e);
        }
        return format("Консоль запущена %s", Arrays.toString(args));
    }
}
