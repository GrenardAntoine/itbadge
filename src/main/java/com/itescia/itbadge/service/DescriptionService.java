package com.itescia.itbadge.service;

import com.itescia.itbadge.domain.Description;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Description.
 */
public interface DescriptionService {

    /**
     * Save a description.
     *
     * @param description the entity to save
     * @return the persisted entity
     */
    Description save(Description description);

    /**
     * Get all the descriptions.
     *
     * @return the list of entities
     */
    List<Description> findAll();


    /**
     * Get the "id" description.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Description> findOne(Long id);

    /**
     * Delete the "id" description.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
