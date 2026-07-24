package com.cognizant.loan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Mandatory: "Creating Microservices for account and loan" - Loan Microservice
 * Method: GET
 * Endpoint: /loans/{number}
 * Sample Response: { "number": "H00987987972342", "type": "car", "loan": 400000, "emi": 3258, "tenure": 18 }
 * (dummy response - no backend connectivity, as instructed in the hands-on)
 */
@RestController
public class LoanController {

    @GetMapping("/loans/{number}")
    public Map<String, Object> getLoan(@PathVariable String number) {
        Map<String, Object> loan = new LinkedHashMap<>();
        loan.put("number", number);
        loan.put("type", "car");
        loan.put("loan", 400000);
        loan.put("emi", 3258);
        loan.put("tenure", 18);
        return loan;
    }
}
