package com.itescia.itbadge.service;

import com.itescia.itbadge.domain.Groupe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Groupe.
 */
public interface GroupeService {

    /**
     * Save a groupe.
     *
     * @param groupe the entity to save
     * @return the persisted entity
     */
    Groupe save(Groupe groupe);

    /**
     * Get all the groupes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Groupe> findAll(Pageable pageable);

    /**
     * Get all the Groupe with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Groupe> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" groupe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Groupe> findOne(Long id);

    /**
     * Delete the "id" groupe.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
