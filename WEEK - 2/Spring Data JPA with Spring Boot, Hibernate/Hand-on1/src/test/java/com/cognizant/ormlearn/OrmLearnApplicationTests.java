package com.cognizant.ormlearn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * OrmLearnApplicationTests - Spring Boot Integration Test Class
 *
 * @SpringBootTest:
 *   - Loads the full Spring ApplicationContext for integration testing.
 *   - Verifies that all beans can be created and wired together correctly.
 *   - If the context fails to start (e.g., wrong DB config, missing beans),
 *     this test will FAIL, giving early feedback about configuration issues.
 *
 * The contextLoads() test:
 *   - Is intentionally empty.
 *   - Simply verifies that the application context loads without errors.
 *   - Acts as a smoke test for the entire Spring Boot setup.
 */
@SpringBootTest
class OrmLearnApplicationTests {

    /**
     * contextLoads() - Smoke Test
     *
     * This test passes if:
     *   1. Spring Boot starts successfully.
     *   2. Database connection is established.
     *   3. Schema validation passes (all entity fields match table columns).
     *   4. All @Service and @Repository beans are created and injected.
     *
     * This test fails if:
     *   1. application.properties has wrong DB credentials.
     *   2. The 'country' table doesn't exist in the 'ormlearn' schema.
     *   3. @Column names in Country.java don't match actual column names.
     */
    @Test
    void contextLoads() {
        // If Spring context loads without exception, this test passes automatically.
    }
}
