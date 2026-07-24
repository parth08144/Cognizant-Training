package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    /**
     * "REST - Country Web Service" (mandatory)
     * GET /country -> returns the India country bean
     * Sample Response: {"code": "IN", "name": "India"}
     */
    @GetMapping("/country")
    public Country getCountryIndia() {
        LOGGER.info("Start");
        Country country = countryService.getCountryIndia();
        LOGGER.debug("country={}", country);
        LOGGER.info("End");
        return country;
    }

    /**
     * "REST - Get all countries" (supporting, not individually mandatory)
     * GET /countries -> returns all 4 countries loaded from country.xml
     */
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.info("End");
        return countries;
    }

    /**
     * "REST - Get country based on country code" (mandatory)
     * GET /countries/{code} -> case-insensitive lookup
     * Sample Request : http://localhost:8083/countries/in
     * Sample Response: {"code": "IN", "name": "India"}
     * On not found -> 404 with CountryNotFoundException's @ResponseStatus reason
     */
    @GetMapping("/countries/{code}")
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("Start");
        Country country = countryService.getCountry(code);
        LOGGER.debug("country={}", country);
        LOGGER.info("End");
        return country;
    }
}
