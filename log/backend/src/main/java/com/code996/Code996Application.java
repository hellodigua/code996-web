package com.code996;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Code996 Backend Application
 * Git repository online analysis service
 */
@SpringBootApplication
@EnableScheduling
public class Code996Application {

    public static void main(String[] args) {
        SpringApplication.run(Code996Application.class, args);
        System.out.println("\n==============================================");
        System.out.println("Code996 Backend Service Started Successfully!");
        System.out.println("API Endpoint: http://localhost:8080/api");
        System.out.println("==============================================\n");
    }
}
