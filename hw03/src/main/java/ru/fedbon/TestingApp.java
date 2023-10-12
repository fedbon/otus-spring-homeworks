package ru.fedbon;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.fedbon.controller.AppController;


@SpringBootApplication
public class TestingApp {
    public static void main(String[] args) throws BeansException {
        var ctx = SpringApplication.run(TestingApp.class);
        var appController = ctx.getBean(AppController.class);
        appController.executeTesting();
    }
}
