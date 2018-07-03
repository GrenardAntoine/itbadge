package com.itescia.itbadge.service;

import com.itescia.itbadge.domain.Utilisateur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Utilisateur.
 */
public interface UtilisateurService {

    /**
     * Save a utilisateur.
     *
     * @param utilisateur the entity to save
     * @return the persisted entity
     */
    Utilisateur save(Utilisateur utilisateur);

    /**
     * Get all the utilisateurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Utilisateur> findAll(Pageable pageable);

    /**
     * Get all the Utilisateur with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Utilisateur> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" utilisateur.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Utilisateur> findOne(Long id);

    /**
     * Delete the "id" utilisateur.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    Optional<Utilisateur> getCurrentUtilisateur();

    Page<Utilisateur> findStudentAndBadgeage(Pageable pageable);
}
