package com.cognizant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ============================================================
 * HelloController — REST Controller for Hands On 3
 * ============================================================
 *
 * @RestController is a composed annotation that combines:
 *
 *   1. @Controller
 *      Marks this class as a Spring MVC controller.
 *      The DispatcherServlet routes incoming HTTP requests to it.
 *
 *   2. @ResponseBody
 *      Tells Spring to write the return value of each method
 *      directly into the HTTP response body (not into a view/template).
 *      Since we return a plain String, the response body = "Hello World!!"
 *      with Content-Type: text/plain.
 *
 * Spring's @ComponentScan (inherited from @SpringBootApplication) detects
 * this class and registers it as a bean in the ApplicationContext.
 * ============================================================
 */
@RestController
public class HelloController {

    /**
     * SLF4J logger for this controller.
     * Logs start/end of each request as required by the exercise.
     */
    private static final Logger logger =
            LoggerFactory.getLogger(HelloController.class);

    /**
     * sayHello() — Handles HTTP GET requests to /hello
     * =================================================
     *
     * @GetMapping("/hello")
     *   Maps HTTP GET /hello to this method.
     *   Equivalent to: @RequestMapping(value="/hello", method=RequestMethod.GET)
     *
     * Return type: String
     *   @RestController + String return → Spring writes the String directly
     *   into the HTTP response body with Content-Type: text/plain;charset=UTF-8
     *
     * Sample Request  : GET http://localhost:8083/hello
     * Sample Response : Hello World!!
     *
     * @return the hardcoded greeting string "Hello World!!"
     */
    @GetMapping("/hello")
    public String sayHello() {

        // START log — logs the beginning of the request handling
        logger.info("--- START : HelloController.sayHello() ---");
        logger.info("Received GET request at /hello");

        // Business logic: return the hardcoded greeting
        String response = "Hello World!!";

        // END log — logs the end of the request handling
        logger.info("Response  : {}", response);
        logger.info("--- END   : HelloController.sayHello() ---");

        return response;
    }
}
