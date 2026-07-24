package com.cognizant.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Mandatory: "Creating Microservices for account and loan" - Account Microservice
 * Additional: @EnableDiscoveryClient registers this service with Eureka once the
 * eureka-discovery-server (in this same folder) is running.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
