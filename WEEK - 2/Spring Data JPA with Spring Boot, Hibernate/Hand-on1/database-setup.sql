CREATE SCHEMA IF NOT EXISTS ormlearn;

USE ormlearn;

CREATE TABLE IF NOT EXISTS country (
    co_code VARCHAR(2)  NOT NULL PRIMARY KEY,
    co_name VARCHAR(50) NOT NULL
);

INSERT INTO country VALUES ('IN', 'India');
INSERT INTO country VALUES ('US', 'United States of America');

SELECT * FROM country;
