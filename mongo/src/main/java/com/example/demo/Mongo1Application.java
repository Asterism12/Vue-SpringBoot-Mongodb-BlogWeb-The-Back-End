package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com")
@EnableAutoConfiguration
public class Mongo1Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Mongo1Application.class, args);
    }

}
