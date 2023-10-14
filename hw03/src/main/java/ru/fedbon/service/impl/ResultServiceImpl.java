package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.config.ScoreToPassProvider;
import ru.fedbon.domain.Result;
import ru.fedbon.service.IOService;
import ru.fedbon.service.ResultService;
import ru.fedbon.service.Stringifier;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final IOService ioService;

    private final Stringifier stringifier;

    private final ScoreToPassProvider propertiesProvider;

    @Override
    public void displayResult(Result result) {
        ioService.output(stringifier.stringifyResult(result, propertiesProvider.getScoreToPass()));
    }
}
