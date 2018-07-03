package com.itescia.itbadge.service.impl;

import com.itescia.itbadge.domain.Groupe;
import com.itescia.itbadge.domain.Utilisateur;
import com.itescia.itbadge.service.CoursService;
import com.itescia.itbadge.domain.Cours;
import com.itescia.itbadge.repository.CoursRepository;
import com.itescia.itbadge.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.LocalDate;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing Cours.
 */
@Service
@Transactional
public class CoursServiceImpl implements CoursService {

    private final Logger log = LoggerFactory.getLogger(CoursServiceImpl.class);

    private final CoursRepository coursRepository;
    private final UtilisateurService utilisateurService;

    public CoursServiceImpl(CoursRepository coursRepository, UtilisateurService utilisateurService) {
        this.coursRepository = coursRepository;
        this.utilisateurService = utilisateurService;
    }

    /**
     * Save a cours.
     *
     * @param cours the entity to save
     * @return the persisted entity
     */
    @Override
    public Cours save(Cours cours) {
        log.debug("Request to save Cours : {}", cours);        return coursRepository.save(cours);
    }

    /**
     * Get all the cours.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cours> findAll(Pageable pageable) {
        log.debug("Request to get all Cours");
        return coursRepository.findAll(pageable);
    }


    /**
     * Get one cours by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cours> findOne(Long id) {
        log.debug("Request to get Cours : {}", id);
        return coursRepository.findById(id);
    }

    /**
     * Delete the cours by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cours : {}", id);
        coursRepository.deleteById(id);
    }

    @Override
    public Optional<Cours> findOneByUtilisateurAndDateDebut() {
        Optional<Utilisateur> utilisateur = utilisateurService.getCurrentUtilisateur();
        Instant DateDebut = Instant.now();
        if(utilisateur.get().isIsProfesseur()) {
            Set<Utilisateur> listUtilisateur = new AbstractSet<Utilisateur>() {
                @Override
                public Iterator<Utilisateur> iterator() {
                    return null;
                }

                @Override
                public int size() {
                    return 0;
                }
            };
            listUtilisateur.add(utilisateur.get());
            return coursRepository.findOneByListProfesseursContainsAndDateDebut(utilisateur.get(),DateDebut);
        }
        else {
            Set<Groupe> listGroupe = new AbstractSet<Groupe>() {
                @Override
                public Iterator<Groupe> iterator() {
                    return null;
                }

                @Override
                public int size() {
                    return 0;
                }
            };
            listGroupe.add(utilisateur.get().getGroupe());
            return coursRepository.findOneByListGroupesContainsAndDateDebut(utilisateur.get().getGroupe(), DateDebut);
        }
    }

    @Override
    public Page<Cours> findByListProfesseurs(Pageable pageable) {
        Optional<Utilisateur> utilisateur = utilisateurService.getCurrentUtilisateur();
        Instant DateDebut = Instant.now();
        if(utilisateur.get().isIsProfesseur()) {
            Set<Utilisateur> listUtilisateur = new AbstractSet<Utilisateur>() {
                @Override
                public Iterator<Utilisateur> iterator() {
                    return null;
                }

                @Override
                public int size() {
                    return 0;
                }
            };
            listUtilisateur.add(utilisateur.get());
            return coursRepository.findByListProfesseurs(listUtilisateur,pageable);
        }
        return null;
    }


    @Override
    public Page<Cours> findByListProfesseursUnique(Pageable pageable) {
        Optional<Utilisateur> utilisateur = utilisateurService.getCurrentUtilisateur();
        Instant DateDebut = Instant.now();
        if(utilisateur.get().isIsProfesseur()) {
            Set<Utilisateur> listUtilisateur = new AbstractSet<Utilisateur>() {
                @Override
                public Iterator<Utilisateur> iterator() {
                    return null;
                }

                @Override
                public int size() {
                    return 0;
                }
            };
            listUtilisateur.add(utilisateur.get());
            return coursRepository.findByListProfesseursUnique(listUtilisateur,pageable);
        }
        return null;
    }
}
