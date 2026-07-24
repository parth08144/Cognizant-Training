package com.cognizant.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class TaskManagerApplication {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskManagerApplication.class);

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
