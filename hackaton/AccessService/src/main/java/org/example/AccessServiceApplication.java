package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccessServiceApplication.class, args);
        System.out.println("App is working");
    }

}