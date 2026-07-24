package com.cognizant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger logger =
            LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String sayHello() {

        logger.info("--- START : HelloController.sayHello() ---");
        logger.info("Received GET request at /hello");

        String response = "Hello World!!";

        logger.info("Response  : {}", response);
        logger.info("--- END   : HelloController.sayHello() ---");

        return response;
    }
}
