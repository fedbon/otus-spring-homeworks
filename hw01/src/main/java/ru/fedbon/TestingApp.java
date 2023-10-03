package ru.fedbon;

import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.fedbon.service.TestingServiceImpl;
import ru.fedbon.service.api.TestingService;

public class TestingApp {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestingService testingService = context.getBean(TestingServiceImpl.class);
        testingService.executeTesting();
    }
}
