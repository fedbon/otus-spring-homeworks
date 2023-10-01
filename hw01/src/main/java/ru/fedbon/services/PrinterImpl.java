package ru.fedbon.services;

import lombok.AllArgsConstructor;
import ru.fedbon.services.api.Printer;

import java.io.PrintStream;
import java.util.List;

@AllArgsConstructor
public class PrinterImpl implements Printer {
    private final PrintStream out;

    public void print(List<String> list) {
        list.forEach(out::println);
    }

}
