package com.cognizant.springlearn.service;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * "REST - Country Web Service" / "REST - Get all countries" /
 * "REST - Get country based on country code" (all mandatory except get-all,
 * which is included as supporting infrastructure).
 *
 * The country master data is intentionally loaded the "Spring Core" way -
 * from country.xml via ClassPathXmlApplicationContext - exactly as instructed
 * in Hands on 4, instead of being hard-coded in this class.
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    private final ApplicationContext countryContext;
    private final List<Country> countryList;

    @SuppressWarnings("unchecked")
    public CountryService() {
        LOGGER.info("Loading country.xml");
        this.countryContext = new ClassPathXmlApplicationContext("country.xml");
        this.countryList = (List<Country>) countryContext.getBean("countryList");
    }

    /** "REST - Country Web Service": load India bean from spring xml configuration and return */
    public Country getCountryIndia() {
        LOGGER.info("Start");
        Country india = countryContext.getBean("in", Country.class);
        LOGGER.info("End");
        return india;
    }

    /** "REST - Get all countries": load country list from country.xml and return */
    public List<Country> getAllCountries() {
        LOGGER.info("Start");
        LOGGER.debug("countries={}", countryList);
        LOGGER.info("End");
        return countryList;
    }

    /**
     * "REST - Get country based on country code" (mandatory)
     * Case-insensitive match against the country list loaded from country.xml.
     */
    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("Start");
        Country match = countryList.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new CountryNotFoundException("Country not found for code: " + code));
        LOGGER.info("End");
        return match;
    }
}
