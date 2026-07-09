-- ============================================================
-- Hand-on 1: Spring Data JPA - MySQL Database Setup Script
-- ============================================================
-- Run these commands in MySQL Workbench or MySQL CLI:
--   > mysql -u root -p
-- ============================================================

-- Step 1: Create the schema (database) named 'ormlearn'
CREATE SCHEMA IF NOT EXISTS ormlearn;

-- Step 2: Switch to the ormlearn schema
USE ormlearn;

-- Step 3: Create the 'country' table
--   co_code -> VARCHAR(2), Primary Key (e.g., 'IN', 'US')
--   co_name -> VARCHAR(50), Full country name
CREATE TABLE IF NOT EXISTS country (
    co_code VARCHAR(2)  NOT NULL PRIMARY KEY,
    co_name VARCHAR(50) NOT NULL
);

-- Step 4: Insert sample country records
INSERT INTO country VALUES ('IN', 'India');
INSERT INTO country VALUES ('US', 'United States of America');

-- Step 5: Verify data was inserted correctly
SELECT * FROM country;

-- Expected Output:
-- +---------+--------------------------+
-- | co_code | co_name                  |
-- +---------+--------------------------+
-- | IN      | India                    |
-- | US      | United States of America |
-- +---------+--------------------------+
