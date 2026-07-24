package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hands on 4: Spring Core - Load Country from Spring Configuration XML
 * Plain POJO wired up as a bean in country.xml (not a Spring Boot / JPA entity).
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    private String code;
    private String name;

    public Country() {
        LOGGER.debug("Inside Country Constructor.");
    }

    public String getCode() {
        LOGGER.debug("getCode() called, value={}", code);
        return code;
    }

    public void setCode(String code) {
        LOGGER.debug("setCode() called, value={}", code);
        this.code = code;
    }

    public String getName() {
        LOGGER.debug("getName() called, value={}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("setName() called, value={}", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{code='" + code + "', name='" + name + "'}";
    }
}
