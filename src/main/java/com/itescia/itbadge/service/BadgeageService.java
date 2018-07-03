package com.itescia.itbadge.service;

import com.itescia.itbadge.domain.Badgeage;

import com.itescia.itbadge.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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


    Page<Badgeage> findByUtilisateurAndCurrentDate(Pageable pageable);

    Optional<Badgeage> findByUtilisateur(Utilisateur utilisateur);
}
