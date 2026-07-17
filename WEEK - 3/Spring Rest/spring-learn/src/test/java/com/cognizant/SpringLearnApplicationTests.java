package com.cognizant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ============================================================
 * SpringLearnApplicationTests — Integration Test Class
 * ============================================================
 * Location : src/test/java/com/cognizant/
 *
 * @SpringBootTest
 *   Bootstraps the full Spring ApplicationContext for testing.
 *   Ensures all beans load without errors — a basic "smoke test".
 *
 * JUnit 5 (@Test) is included via spring-boot-starter-test.
 * ============================================================
 */
@SpringBootTest
class SpringLearnApplicationTests {

    /**
     * contextLoads() — Verifies that the Spring ApplicationContext
     * starts up successfully with no configuration errors.
     * If any bean fails to initialise, this test will FAIL,
     * alerting you to misconfigured beans or missing dependencies.
     */
    @Test
    void contextLoads() {
        // No assertions needed — the test passes if the context
        // loads without throwing an exception.
        System.out.println("✅ Spring ApplicationContext loaded successfully!");
    }

}
