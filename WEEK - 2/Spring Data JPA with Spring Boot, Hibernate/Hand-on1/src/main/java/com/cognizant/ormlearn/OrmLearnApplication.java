package com.cognizant.ormlearn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;

/**
 * OrmLearnApplication - Spring Boot Main Application Class
 *
 * @SpringBootApplication is a convenience annotation that combines three annotations:
 *
 *   1. @Configuration
 *      - Marks this class as a source of Spring bean definitions.
 *      - Allows defining @Bean methods inside this class.
 *
 *   2. @EnableAutoConfiguration
 *      - Tells Spring Boot to automatically configure your application based on
 *        the dependencies present on the classpath.
 *      - Example: Since spring-boot-starter-data-jpa is on classpath, Spring Boot
 *        automatically configures a DataSource, EntityManagerFactory, and
 *        TransactionManager without any XML configuration.
 *
 *   3. @ComponentScan
 *      - Tells Spring to scan this package (com.cognizant.ormlearn) and all
 *        sub-packages for @Component, @Service, @Repository, @Controller beans
 *        and register them in the Application Context.
 */
@SpringBootApplication
public class OrmLearnApplication {

    /**
     * SLF4J Logger setup:
     *   - Logger is the interface for logging (from SLF4J API).
     *   - LoggerFactory creates the logger bound to a concrete logging framework
     *     (Logback in Spring Boot's default setup).
     *   - We pass the current class (OrmLearnApplication.class) so log messages
     *     show which class generated them.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    /**
     * Static reference to CountryService.
     *   - We need it static because main() is a static method and cannot directly
     *     access instance members.
     *   - It will be populated from the Spring ApplicationContext after startup.
     */
    private static CountryService countryService;

    /**
     * main() - Application Entry Point
     *
     * How SpringApplication.run() works:
     *   1. Creates a Spring ApplicationContext (IoC Container).
     *   2. Performs component scanning (finds all @Service, @Repository etc.).
     *   3. Configures DataSource, EntityManagerFactory, TransactionManager via Auto-configuration.
     *   4. Validates the database schema against the JPA entities (ddl-auto=validate).
     *   5. Returns the ApplicationContext so we can retrieve beans from it.
     *
     * @param args - Command-line arguments (not used here)
     */
    public static void main(String[] args) {
        // Step 1: Start Spring Boot and get the Application Context
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);

        // Step 2: Log that main() was called (verifies our logging configuration works)
        LOGGER.info("Inside main");

        // Step 3: Get the CountryService bean from the Spring container
        // context.getBean() looks up the bean by type from the ApplicationContext
        countryService = context.getBean(CountryService.class);

        // Step 4: Call the test method to verify data retrieval from the database
        testGetAllCountries();
    }

    /**
     * testGetAllCountries() - Test method to verify database connectivity.
     *
     * Purpose:
     *   Calls CountryService.getAllCountries() which internally uses
     *   CountryRepository.findAll() to run:
     *       SELECT co_code, co_name FROM country;
     *
     * Logging levels used:
     *   - LOGGER.info()  -> Printed when logging.level.com.cognizant=info or debug
     *   - LOGGER.debug() -> Printed only when logging.level.com.cognizant=debug
     *
     * Expected output in console:
     *   [INFO]  Start
     *   [TRACE] Hibernate: select ...  (SQL query from Hibernate)
     *   [DEBUG] countries=[Country{code='IN', name='India'}, Country{code='US', name='United States of America'}]
     *   [INFO]  End
     */
    private static void testGetAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("countries={}", countries);
        LOGGER.info("End");
    }
}
