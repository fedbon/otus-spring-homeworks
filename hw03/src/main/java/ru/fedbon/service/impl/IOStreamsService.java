package ru.fedbon.service.impl;

import ru.fedbon.service.IOService;
import ru.fedbon.service.LocalizationMessageService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IOStreamsService implements IOService {

    private final PrintStream out;

    private final Scanner in;

    private final LocalizationMessageService messageService;

    public IOStreamsService(PrintStream out, InputStream in, LocalizationMessageService messageService) {
        this.out = out;
        this.in = new Scanner(in);
        this.messageService = messageService;
    }

    @Override
    public void output(String message) {
        out.println(message);
    }

    @Override
    public String readLn(String prompt) {
        output(prompt);
        return in.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        int userInput = 0;
        boolean validInput = false;

        do {
            output(prompt);
            try {
                userInput = in.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                in.next();
                output(messageService.getLocalizedMessage("error.message.invalid.number.input"));
            }
        } while (!validInput);

        return userInput;
    }
}
