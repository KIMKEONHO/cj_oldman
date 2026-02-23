package com.example.cj_alimtalk_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CjAlimtalkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CjAlimtalkServiceApplication.class, args);
    }
}
