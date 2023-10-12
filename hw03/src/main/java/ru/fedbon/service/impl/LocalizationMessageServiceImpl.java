package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.fedbon.service.LocalizationMessageService;
import ru.fedbon.config.LocaleProvider;


@Service
@RequiredArgsConstructor
public class LocalizationMessageServiceImpl implements LocalizationMessageService {

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    @Override
    public String getLocalizedMessage(String code) {
        return messageSource.getMessage(code, new String[0], localeProvider.getLocale());
    }
}
