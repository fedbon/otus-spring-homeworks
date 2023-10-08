package ru.fedbon.service;

import ru.fedbon.service.api.IOService;
import ru.fedbon.utils.Message;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IOStreamsService implements IOService {
    private final PrintStream out;

    private final Scanner in;

    public IOStreamsService(PrintStream out, InputStream in) {
        this.out = out;
        this.in = new Scanner(in);
    }

    @Override
    public void output(String message) {
        out.println(message);
    }

    @Override
    public String readLn(String prompt) {
        while (true) {
            output(prompt);
            String userInput = in.nextLine();
            if (userInput.matches("[a-zA-Z ]+")) {
                return userInput;
            } else {
                output(Message.MSG_INVALID_STRING_INPUT);
            }
        }
    }

    @Override
    public int readInt(String prompt, int maxOption) {
        while (true) {
            try {
                output(prompt);
                int answer = in.nextInt();
                if (answer >= 1 && answer <= maxOption) {
                    return answer;
                } else {
                    output(Message.MSG_INVALID_INT_INPUT + maxOption + ".");
                }
            } catch (InputMismatchException e) {
                in.next();
                output(Message.MSG_INVALID_INT_INPUT);
            }
        }
    }
}
