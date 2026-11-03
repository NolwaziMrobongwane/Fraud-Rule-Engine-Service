package com.bank.fraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FraudRuleEngineApplication {
    public static void main(String[] args) {
        SpringApplication.run(FraudRuleEngineApplication.class, args);
        System.out.println("Fraud Rule Engine is running on http://localhost:8080");
    }
}
