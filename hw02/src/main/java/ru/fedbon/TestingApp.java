package ru.fedbon;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.fedbon.controller.TestingAppController;


@PropertySource("classpath:application.properties")
@ComponentScan
@Configuration
public class TestingApp {
    public static void main(String[] args) throws BeansException {
        var context = new AnnotationConfigApplicationContext(TestingApp.class);
        var testingAppController = context.getBean(TestingAppController.class);
        testingAppController.executeTesting();
    }
}
