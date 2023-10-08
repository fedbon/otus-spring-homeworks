package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.domain.User;
import ru.fedbon.service.api.IOService;
import ru.fedbon.service.api.UserService;
import ru.fedbon.utils.Message;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final IOService ioService;

    @Override
    public User getUser() {
        ioService.output(Message.MSG_INTRODUCE);
        var firstName = ioService.readLn(Message.MSG_ENTER_FIRST_NAME);
        var lastName = ioService.readLn(Message.MSG_ENTER_LAST_NAME);
        return new User(firstName, lastName);
    }
}
