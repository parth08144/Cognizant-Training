package com.cognizant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        // Bootstrap the Spring Boot application
        SpringApplication.run(SpringLearnApplication.class, args);

        // Log: application started successfully
        logger.info("========================================");
        logger.info("  SpringLearnApplication STARTED        ");
        logger.info("  Server running at http://localhost:8080");
        logger.info("========================================");
    }
}
