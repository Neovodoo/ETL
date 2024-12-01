package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ETLApplication {

    public static void main(String[] args) {
        SpringApplication.run(ETLApplication.class, args);
        System.out.println("App is working");
    }

}