package com.itescia.itbadge.service.impl;

import com.itescia.itbadge.domain.Cours;
import com.itescia.itbadge.domain.Groupe;
import com.itescia.itbadge.repository.CoursRepository;
import com.itescia.itbadge.repository.GroupeRepository;
import com.itescia.itbadge.security.SecurityUtils;
import com.itescia.itbadge.service.UploadService;
import com.itescia.itbadge.service.UtilisateurService;
import com.itescia.itbadge.domain.Utilisateur;
import com.itescia.itbadge.repository.UtilisateurRepository;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

/**
 * Service Implementation for managing Utilisateur.
 */
@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    private final Logger log = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

    private final UtilisateurRepository utilisateurRepository;
    private final CoursRepository coursRepository;
    private final GroupeRepository groupeRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, CoursRepository coursRepository, GroupeRepository groupeRepository) {
        this.coursRepository = coursRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.groupeRepository = groupeRepository;
    }

    /**
     * Save a utilisateur.
     *
     * @param utilisateur the entity to save
     * @return the persisted entity
     */
    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        log.debug("Request to save Utilisateur : {}", utilisateur);        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Get all the utilisateurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Utilisateur> findAll(Pageable pageable) {
        log.debug("Request to get all Utilisateurs");
        return utilisateurRepository.findAll(pageable);
    }

    /**
     * Get all the Utilisateur with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Utilisateur> findAllWithEagerRelationships(Pageable pageable) {
        return utilisateurRepository.findAllWithEagerRelationships(pageable);
    }


    /**
     * Get one utilisateur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Utilisateur> findOne(Long id) {
        log.debug("Request to get Utilisateur : {}", id);
        return utilisateurRepository.findOneWithEagerRelationships(id);
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Utilisateur> findOneByLogin(String login) {
        log.debug("Request to get Utilisateur : {}", login);
        return utilisateurRepository.findOneByUserLogin(login);
    }

    /**
     * Delete the utilisateur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Utilisateur : {}", id);
        utilisateurRepository.deleteById(id);
    }



    @Override
    public Optional<Utilisateur> getCurrentUtilisateur() {

        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        return utilisateurRepository.findOneByUserLogin(login.get());

    }

    @Override
    public Page<Utilisateur> findStudentAndBadgeage(Pageable pageable) {
        Optional<Utilisateur> utilisateur = getCurrentUtilisateur();
        Instant currentDate = Instant.now();
        Optional<Cours> currentCours = coursRepository.findOneByListProfesseursContainsAndDateDebutBeforeAndDateFinAfter(utilisateur.get(),currentDate,currentDate);
        currentCours.get().setListGroupes(groupeRepository.findByListCoursContains(currentCours.get()));

        return utilisateurRepository.findByCours(currentCours.get(),currentCours.get().getListGroupes(),pageable);

    }
    
    public void insertUtilisateur() throws EncryptedDocumentException, InvalidFormatException, IOException {
    	UploadService u = new UploadService(utilisateurRepository, coursRepository, groupeRepository);
    	u.uploadUtilisateur();
    }
    
}
