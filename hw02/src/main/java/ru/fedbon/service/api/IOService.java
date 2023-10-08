package ru.fedbon.service.api;

public interface IOService {
    void output(String message);

    String readLn(String prompt);

    int readInt(String prompt, int maxOption);
}
