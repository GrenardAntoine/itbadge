package com.itescia.itbadge.service;

import com.itescia.itbadge.domain.Cours;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Cours.
 */
public interface CoursService {

    /**
     * Save a cours.
     *
     * @param cours the entity to save
     * @return the persisted entity
     */
    Cours save(Cours cours);

    /**
     * Get all the cours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Cours> findAll(Pageable pageable);


    /**
     * Get the "id" cours.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Cours> findOne(Long id);

    /**
     * Delete the "id" cours.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Optional<Cours> findOneByUtilisateurAndDateDebut();

    Page<Cours> findByListProfesseurs(Pageable pageable);

    Page<Cours> findByListProfesseursUnique(Pageable pageable);

    Page<Cours> getListCoursByCurrentProfesseur(Pageable pageable);
}
