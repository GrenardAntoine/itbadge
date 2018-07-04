package com.itescia.itbadge.service.impl;

import com.itescia.itbadge.domain.Utilisateur;
import com.itescia.itbadge.service.BadgeageService;
import com.itescia.itbadge.domain.Badgeage;
import com.itescia.itbadge.repository.BadgeageRepository;
import com.itescia.itbadge.service.UtilisateurService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Badgeage.
 */
@Service
@Transactional
public class BadgeageServiceImpl implements BadgeageService {

    private final Logger log = LoggerFactory.getLogger(BadgeageServiceImpl.class);

    private final BadgeageRepository badgeageRepository;
    private final UtilisateurService utilisateurService;

    public BadgeageServiceImpl(BadgeageRepository badgeageRepository, UtilisateurService utilisateurService) {
        this.badgeageRepository = badgeageRepository;
        this.utilisateurService = utilisateurService;
    }

    /**
     * Save a badgeage.
     *
     * @param badgeage the entity to save
     * @return the persisted entity
     */
    @Override
    public Badgeage save(Badgeage badgeage) {
        log.debug("Request to save Badgeage : {}", badgeage);        return badgeageRepository.save(badgeage);
    }

    /**
     * Get all the badgeages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Badgeage> findAll(Pageable pageable) {
        log.debug("Request to get all Badgeages");
        return badgeageRepository.findAll(pageable);
    }


    /**
     * Get one badgeage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Badgeage> findOne(Long id) {
        log.debug("Request to get Badgeage : {}", id);
        return badgeageRepository.findById(id);
    }

    /**
     * Delete the badgeage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Badgeage : {}", id);
        badgeageRepository.deleteById(id);
    }

    @Override
    public Page<Badgeage> findByUtilisateur(Pageable pageable) {
        Optional<Utilisateur> utilisateur = utilisateurService.getCurrentUtilisateur();
        return badgeageRepository.findByUtilisateur(utilisateur.get(), pageable);
    }


    @Override
    public Page<Badgeage> findByUtilisateurAndCurrentDate(Pageable pageable) {
        Optional<Utilisateur> utilisateur = utilisateurService.getCurrentUtilisateur();
        LocalDate currentDate = LocalDate.now();
        return badgeageRepository.findByUtilisateurAndCurrentDate(utilisateur.get(), currentDate, pageable);
    }

    @Override
    public Optional<Badgeage> findByUtilisateur(Utilisateur utilisateur) {
        return badgeageRepository.findByUtilisateur(utilisateur);
    }

    @Override
    public Optional<Badgeage> findIfBadgeageExist() {
        Optional<Badgeage> badgeage = null;
        Optional<Utilisateur> utilisateur = utilisateurService.getCurrentUtilisateur();
        Instant currentDate = Instant.now();
        LocalDateTime matin = LocalDateTime.now();
        if(matin.getHour() <= 12 || (matin.getHour() == 12 && matin.getMinute() < 30))
        {
            while(matin.getHour() == 12)
            {
                matin.plusHours(1);
            }
            while(matin.getMinute() == 30)
            {
                if(matin.getMinute() < 30)
                    matin.plusMinutes(1);
                else
                    matin.minusMinutes(1);

            }
            badgeageRepository.findIfBadgeageExist(utilisateur.get(),matin.toInstant(ZoneOffset.UTC));
        }

        LocalDateTime midi = LocalDateTime.now();
        if(midi.getHour() <= 23 || (midi.getHour() == 23 && matin.getMinute() < 30))
        {
            while(midi.getHour() == 23)
            {
                midi.plusHours(1);
            }
            while(midi.getMinute() == 30)
            {
                if(midi.getMinute() < 30)
                    midi.plusMinutes(1);
                else
                    midi.minusMinutes(1);

            }
            badgeageRepository.findIfBadgeageExist(utilisateur.get(),midi.toInstant(ZoneOffset.UTC));
        }

        return badgeage;
    }
}
