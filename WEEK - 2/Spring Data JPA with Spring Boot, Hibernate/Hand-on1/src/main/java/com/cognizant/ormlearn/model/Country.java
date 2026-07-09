package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Country - JPA Entity class
 *
 * This class maps to the 'country' table in the ormlearn MySQL schema.
 *
 * Annotations used:
 *  @Entity  -> Marks this class as a JPA managed entity (persistent object).
 *              Spring Data JPA will manage its lifecycle (CRUD operations).
 *  @Table   -> Specifies the exact table name in the database this entity maps to.
 *              Without this, JPA would default to the class name "Country" as table name.
 */
@Entity
@Table(name = "country")
public class Country {

    /**
     * @Id     -> Marks 'code' as the primary key of this entity.
     * @Column -> Maps this field to the 'co_code' column in the database.
     *            'name' attribute overrides the default column name mapping.
     */
    @Id
    @Column(name = "co_code")
    private String code;

    /**
     * @Column -> Maps this field to the 'co_name' column in the database.
     */
    @Column(name = "co_name")
    private String name;

    // -------------------------------------------------------
    // Default no-arg constructor (required by JPA specification)
    // JPA needs to instantiate the entity via reflection
    // -------------------------------------------------------
    public Country() {
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------

    /**
     * Returns the country code (primary key).
     * Example: "IN", "US"
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the country code (primary key).
     * Example: "IN", "US"
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the full country name.
     * Example: "India", "United States of America"
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full country name.
     */
    public void setName(String name) {
        this.name = name;
    }

    // -------------------------------------------------------
    // toString() - Useful for logging/debugging
    // -------------------------------------------------------

    /**
     * Returns a string representation of this entity.
     * Used by LOGGER.debug() to print country details.
     */
    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
