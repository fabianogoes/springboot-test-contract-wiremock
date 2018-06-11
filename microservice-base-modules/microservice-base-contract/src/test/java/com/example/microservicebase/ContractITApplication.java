package com.example.microservicebase;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableAutoConfiguration
public class ContractITApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(
                ContractITApplication.class
        ).run(args);
    }
}
