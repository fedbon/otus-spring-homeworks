package ru.fedbon;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TestingApp {
    public static void main(String[] args) throws BeansException {
        SpringApplication.run(TestingApp.class, args);
    }
}
