package com.cognizant.greet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Additional: simple microservice returning "Hello World", used to
 * demonstrate the API Gateway routing requests to it via Eureka.
 */
@RestController
public class GreetController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello World";
    }
}
