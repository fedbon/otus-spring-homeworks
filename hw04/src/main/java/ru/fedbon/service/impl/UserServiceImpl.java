package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.domain.User;
import ru.fedbon.service.IOService;
import ru.fedbon.service.LocalizationMessageService;
import ru.fedbon.service.UserService;
import ru.fedbon.validator.LettersOnlyValidatorImpl;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final IOService ioService;

    private final LocalizationMessageService messageService;

    private final LettersOnlyValidatorImpl validator;

    @Override
    public User getUser() {
        ioService.output(messageService.getLocalizedMessage("message.introduce"));

        String firstName;
        String lastName;

        do {
            firstName = ioService.readLn(messageService.getLocalizedMessage("message.enter.first.name"));
            if (validator.validate(firstName)) {
                ioService.output(messageService.getLocalizedMessage(validator.errorMessage()));
            }
        } while (validator.validate(firstName));

        do {
            lastName = ioService.readLn(messageService.getLocalizedMessage("message.enter.last.name"));
            if (validator.validate(lastName)) {
                ioService.output(messageService.getLocalizedMessage(validator.errorMessage()));
            }
        } while (validator.validate(lastName));

        return new User(firstName, lastName);
    }
}
