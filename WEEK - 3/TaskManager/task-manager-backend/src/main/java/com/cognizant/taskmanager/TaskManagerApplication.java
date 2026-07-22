package com.cognizant.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ============================================================
 * TaskManagerApplication — Entry Point for the Spring Boot App
 * ============================================================
 *
 * @SpringBootApplication is a convenience annotation that combines:
 *
 *   1. @Configuration
 *      Marks this class as a source of Spring bean definitions.
 *
 *   2. @EnableAutoConfiguration
 *      Tells Spring Boot to automatically configure the application
 *      based on the JARs on the classpath.
 *
 *   3. @ComponentScan
 *      Scans the current package (com.cognizant.taskmanager) and
 *      sub-packages for @Component, @RestController, @Service, etc.
 */
@SpringBootApplication
public class TaskManagerApplication {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskManagerApplication.class);

    /**
     * main() — Application entry point.
     *
     * SpringApplication.run() will:
     *   1. Create the ApplicationContext (Spring IoC container).
     *   2. Register all beans discovered by @ComponentScan.
     *   3. Trigger auto-configuration.
     *   4. Start the embedded Tomcat server on port 8080.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        logger.info("=========================================");
        logger.info("  Task Manager Application is STARTING   ");
        logger.info("=========================================");

        SpringApplication.run(TaskManagerApplication.class, args);

        logger.info("=========================================");
        logger.info("  Task Manager Application STARTED       ");
        logger.info("  Server running at http://localhost:8080 ");
        logger.info("  API base path:  /api/tasks              ");
        logger.info("=========================================");
    }
}
