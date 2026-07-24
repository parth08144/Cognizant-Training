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

@SpringBootApplication
public class SpringLearnApplication {

    private static final Logger logger =
            LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {

        logger.info("========================================");
        logger.info("  SpringLearnApplication is STARTING   ");
        logger.info("========================================");

        displayDate();

        SpringApplication.run(SpringLearnApplication.class, args);

        logger.info("========================================");
        logger.info("  SpringLearnApplication STARTED        ");
        logger.info("  Server running at http://localhost:8080");
        logger.info("========================================");
    }

    public static void displayDate() {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("date-format.xml");

        SimpleDateFormat format =
                context.getBean("dateFormat", SimpleDateFormat.class);

        try {

            Date date = format.parse("31/12/2018");

            System.out.println("====================================");
            System.out.println("  Hands On 2 — Spring XML Bean Demo ");
            System.out.println("  Input  : 31/12/2018");
            System.out.println("  Parsed : " + date);
            System.out.println("====================================");

        } catch (ParseException e) {

            logger.error("Failed to parse date string: {}", e.getMessage());
        }
    }
}
