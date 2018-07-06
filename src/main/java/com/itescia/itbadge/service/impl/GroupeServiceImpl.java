package com.itescia.itbadge.service.impl;

import com.itescia.itbadge.domain.Cours;
import com.itescia.itbadge.domain.Utilisateur;
import com.itescia.itbadge.service.CoursService;
import com.itescia.itbadge.service.GroupeService;
import com.itescia.itbadge.domain.Groupe;
import com.itescia.itbadge.repository.GroupeRepository;
import com.itescia.itbadge.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing Groupe.
 */
@Service
@Transactional
public class GroupeServiceImpl implements GroupeService {

    private final Logger log = LoggerFactory.getLogger(GroupeServiceImpl.class);

    private final GroupeRepository groupeRepository;
    private final CoursService coursService;

    public GroupeServiceImpl(GroupeRepository groupeRepository,
                             CoursService coursService
    ) {
        this.groupeRepository = groupeRepository;
        this.coursService = coursService;
    }

    /**
     * Save a groupe.
     *
     * @param groupe the entity to save
     * @return the persisted entity
     */
    @Override
    public Groupe save(Groupe groupe) {
        log.debug("Request to save Groupe : {}", groupe);        return groupeRepository.save(groupe);
    }

    /**
     * Get all the groupes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Groupe> findAll(Pageable pageable) {
        log.debug("Request to get all Groupes");
        return groupeRepository.findAll(pageable);
    }

    /**
     * Get all the Groupe with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Groupe> findAllWithEagerRelationships(Pageable pageable) {
        return groupeRepository.findAllWithEagerRelationships(pageable);
    }


    /**
     * Get one groupe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Groupe> findOne(Long id) {
        log.debug("Request to get Groupe : {}", id);
        return groupeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the groupe by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Groupe : {}", id);
        groupeRepository.deleteById(id);
    }

    @Override
    public Set<Groupe> findByCours(Cours cours) {
        return groupeRepository.findByListCoursContains(cours);
    }

    @Override
    public Optional<Groupe> findBadgeageGroupe(LocalDate day, Long groupId) {
        return groupeRepository.findBadgeageGroupe(day, groupId);
    }
    
    @Override
    public Optional<Groupe> findByName(String groupname) {
        return groupeRepository.findByNom(groupname);
    }
}
