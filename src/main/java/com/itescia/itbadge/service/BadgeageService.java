package com.itescia.itbadge.service;

import com.itescia.itbadge.domain.Badgeage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Badgeage.
 */
public interface BadgeageService {

    /**
     * Save a badgeage.
     *
     * @param badgeage the entity to save
     * @return the persisted entity
     */
    Badgeage save(Badgeage badgeage);

    /**
     * Get all the badgeages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Badgeage> findAll(Pageable pageable);


    /**
     * Get the "id" badgeage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Badgeage> findOne(Long id);

    /**
     * Delete the "id" badgeage.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    Page<Badgeage> findByUtilisateur(Pageable pageable);
}
