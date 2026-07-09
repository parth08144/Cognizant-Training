package com.cognizant.ormlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Country;

/**
 * CountryRepository - Spring Data JPA Repository Interface
 *
 * Purpose:
 *   This interface provides CRUD (Create, Read, Update, Delete) operations
 *   for the Country entity WITHOUT writing any SQL or boilerplate DAO code.
 *
 * How it works:
 *   - We extend JpaRepository<Country, String>
 *       -> Country : the Entity type this repository manages
 *       -> String  : the type of the primary key (@Id field) in Country
 *
 *   - Spring Data JPA automatically generates a proxy implementation at runtime
 *     that handles all database operations.
 *
 * Built-in methods provided by JpaRepository (no code needed):
 *   - findAll()        -> SELECT * FROM country
 *   - findById(id)     -> SELECT * FROM country WHERE co_code = ?
 *   - save(entity)     -> INSERT or UPDATE
 *   - deleteById(id)   -> DELETE FROM country WHERE co_code = ?
 *   - count()          -> SELECT COUNT(*) FROM country
 *   - existsById(id)   -> Checks if a record exists
 *
 * @Repository:
 *   - Marks this interface as a Spring-managed Repository bean.
 *   - Enables Spring to detect it via component scanning.
 *   - Also provides exception translation (converts DB exceptions to Spring's
 *     DataAccessException hierarchy).
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    // No additional methods needed for this hands-on.
    // Spring Data JPA provides findAll(), findById(), save(), delete() etc. out of the box.
}
