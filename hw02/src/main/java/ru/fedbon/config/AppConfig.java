package ru.fedbon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fedbon.service.IOStreamsService;
import ru.fedbon.service.api.IOService;


@Configuration
public class AppConfig {

    @Bean
    public IOService ioService() {
        return new IOStreamsService(System.out, System.in);
    }
}
