package ru.fedbon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fedbon.service.impl.IOStreamsService;
import ru.fedbon.service.IOService;


@Configuration
public class IOServiceConfig {

    @Bean
    public IOService ioService() {
        return new IOStreamsService(System.out, System.in);
    }
}
