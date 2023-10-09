package ru.fedbon.service.api;

import ru.fedbon.domain.Result;
import ru.fedbon.domain.User;

public interface TestingService {
    void displayHeader();

    User registryUser();

    Result getResult(User user);

    void displayResult(Result result);

}
