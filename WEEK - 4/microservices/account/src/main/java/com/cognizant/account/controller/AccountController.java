package com.cognizant.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Mandatory: "Creating Microservices for account and loan" - Account Microservice
 * Method: GET
 * Endpoint: /accounts/{number}
 * Sample Response: { "number": "00987987973432", "type": "savings", "balance": 234343 }
 * (dummy response - no backend connectivity, as instructed in the hands-on)
 */
@RestController
public class AccountController {

    @GetMapping("/accounts/{number}")
    public Map<String, Object> getAccount(@PathVariable String number) {
        Map<String, Object> account = new LinkedHashMap<>();
        account.put("number", number);
        account.put("type", "savings");
        account.put("balance", 234343);
        return account;
    }
}
