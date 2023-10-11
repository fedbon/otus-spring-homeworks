package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.domain.Result;
import ru.fedbon.service.IOService;
import ru.fedbon.service.ResultService;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final IOService ioService;

    private final ResultStringifier resultStringifier;

    @Override
    public void displayResult(Result result) {
        ioService.output(resultStringifier.stringify(result, resultStringifier.getScoreToPass()));
    }
}
