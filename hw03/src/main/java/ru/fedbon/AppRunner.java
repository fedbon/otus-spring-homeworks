package ru.fedbon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.fedbon.controller.AppController;

@Component
class AppRunner implements CommandLineRunner {

    private final AppController appController;

    public AppRunner(AppController appController) {
        this.appController = appController;
    }

    @Override
    public void run(String... args) {
        appController.executeTesting();
    }
}
