package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT-handson: "Create authentication service that returns JWT" (mandatory)
 *
 * Request:  curl -s -u user:pwd http://localhost:8083/authenticate
 * Response: {"token": "eyJhbGciOiJIUzI1NiJ9....."}
 *
 * Spring Security's HTTP Basic filter already validates the credentials against
 * the in-memory users defined in SecurityConfig before this method is even
 * invoked - if the credentials are wrong the request never reaches here (401).
 */
@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/authenticate")
    public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
        LOGGER.info("Start");
        LOGGER.debug("authHeader={}", authHeader);

        String user = getUser(authHeader);
        String token = jwtUtil.generateJwt(user);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        LOGGER.info("End");
        return map;
    }

    /** Decode the Base64 "Basic user:pwd" header to extract just the username */
    private String getUser(String authHeader) {
        String encodedCredentials = authHeader.replace("Basic ", "");
        String decoded = new String(Base64.getDecoder().decode(encodedCredentials));
        String user = decoded.substring(0, decoded.indexOf(":"));
        LOGGER.debug("user={}", user);
        return user;
    }
}
