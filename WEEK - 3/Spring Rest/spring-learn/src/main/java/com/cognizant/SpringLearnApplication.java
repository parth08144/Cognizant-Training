package com.cognizant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ============================================================
 * SpringLearnApplication — Entry Point for the Spring Boot App
 * ============================================================
 *
 * @SpringBootApplication is a convenience annotation that combines:
 *
 *   1. @Configuration
 *      Marks this class as a source of Spring bean definitions.
 *      Equivalent to a traditional Spring XML <beans> config file.
 *
 *   2. @EnableAutoConfiguration
 *      Tells Spring Boot to automatically configure the application
 *      based on the JARs present on the classpath. For example,
 *      if spring-boot-starter-web is on the classpath, Spring Boot
 *      auto-configures an embedded Tomcat server and Spring MVC.
 *
 *   3. @ComponentScan
 *      Instructs Spring to scan the current package (com.cognizant)
 *      and all sub-packages for components annotated with
 *      @Component, @Service, @Repository, @Controller, @RestController,
 *      etc., and register them as Spring-managed beans.
 *
 * In short: @SpringBootApplication = Auto-config + Component Scan + Config
 */
@SpringBootApplication
public class SpringLearnApplication {

    /**
     * SLF4J Logger for this class.
     * SLF4J (Simple Logging Facade for Java) is a logging abstraction.
     * Spring Boot auto-configures Logback as the default implementation.
     * Use:  logger.info(), logger.debug(), logger.warn(), logger.error()
     */
    private static final Logger logger =
            LoggerFactory.getLogger(SpringLearnApplication.class);

    /**
     * main() — The application entry point.
     *
     * Steps performed by SpringApplication.run():
     *   1. Creates an ApplicationContext (the Spring IoC container).
     *   2. Registers all beans discovered by @ComponentScan.
     *   3. Triggers auto-configuration based on classpath.
     *   4. Starts the embedded Tomcat web server on port 8080 (default).
     *   5. Publishes application lifecycle events.
     *
     * @param args command-line arguments (passed through to Spring)
     */
    public static void main(String[] args) {
        // Log: verifying that main() is invoked — Step 9 of Hands On 1
        logger.info("========================================");
        logger.info("  SpringLearnApplication is STARTING   ");
        logger.info("========================================");

        // Hands On 2: Load SimpleDateFormat from Spring XML config
        displayDate();

        // Bootstrap the Spring Boot application
        SpringApplication.run(SpringLearnApplication.class, args);

        // Log: application started successfully
        logger.info("========================================");
        logger.info("  SpringLearnApplication STARTED        ");
        logger.info("  Server running at http://localhost:8080");
        logger.info("========================================");
    }

    /**
     * displayDate() — Hands On 2
     * ===========================
     * Demonstrates loading a bean from a Spring XML configuration file.
     *
     * Steps:
     *  1. Create ApplicationContext from 'date-format.xml' on the classpath.
     *     ClassPathXmlApplicationContext reads src/main/resources/date-format.xml
     *     and instantiates all declared beans into the Spring IoC container.
     *
     *  2. Retrieve the 'dateFormat' bean using getBean().
     *     Spring returns the singleton SimpleDateFormat("dd/MM/yyyy") bean.
     *
     *  3. Parse the string '31/12/2018' into a java.util.Date object.
     *
     *  4. Print the parsed Date to the console.
     */
    public static void displayDate() {
        // Step 1: Bootstrap the Spring IoC container from the XML config file.
        // ClassPathXmlApplicationContext looks for 'date-format.xml' in:
        //   src/main/resources/  (which is on the classpath at runtime)
        ApplicationContext context =
                new ClassPathXmlApplicationContext("date-format.xml");

        // Step 2: Retrieve the 'dateFormat' bean by ID and type.
        // getBean(String name, Class<T> requiredType) returns a type-safe bean.
        // Spring returns the singleton SimpleDateFormat("dd/MM/yyyy") instance.
        SimpleDateFormat format =
                context.getBean("dateFormat", SimpleDateFormat.class);

        try {
            // Step 3: Parse the string '31/12/2018' using the dd/MM/yyyy pattern.
            // SimpleDateFormat.parse() converts the String → java.util.Date
            Date date = format.parse("31/12/2018");

            // Step 4: Display the parsed Date object in the console.
            System.out.println("====================================");
            System.out.println("  Hands On 2 — Spring XML Bean Demo ");
            System.out.println("  Input  : 31/12/2018");
            System.out.println("  Parsed : " + date);
            System.out.println("====================================");

        } catch (ParseException e) {
            // ParseException is thrown if the input string does not match
            // the pattern 'dd/MM/yyyy' defined in the bean.
            logger.error("Failed to parse date string: {}", e.getMessage());
        }
    }
}
