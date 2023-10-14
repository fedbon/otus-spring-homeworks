package ru.fedbon.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fedbon.service.LocalizationMessageService;
import ru.fedbon.service.impl.IOStreamsService;
import ru.fedbon.service.IOService;


@Configuration
@RequiredArgsConstructor
public class IOServiceConfig {

    private final LocalizationMessageService messageService;

    @Bean
    public IOService ioService() {
        return new IOStreamsService(System.out, System.in, messageService);
    }
}
