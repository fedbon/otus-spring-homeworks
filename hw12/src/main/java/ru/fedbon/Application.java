package ru.fedbon;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * To log in, please use the following credentials:
 * <pre>
 *     Username: usr
 *     Password: pwd
 * </pre>
 *
 * Enter these details in the respective fields to access user account.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
