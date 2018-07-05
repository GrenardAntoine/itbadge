package com.itescia.itbadge.service.impl;

import com.itescia.itbadge.domain.Utilisateur;
import com.itescia.itbadge.service.BadgeageService;
import com.itescia.itbadge.domain.Badgeage;
import com.itescia.itbadge.repository.BadgeageRepository;
import com.itescia.itbadge.service.UtilisateurService;
import com.itescia.itbadge.web.rest.errors.BadRequestAlertException;
import com.itescia.itbadge.web.rest.errors.CustomParameterizedException;
import org.h2.tools.Console;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

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
    public Badgeage addBadgageUser() {

        DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern ( "yyyy-MM-dd HH:mm:ss" )
            .withLocale( Locale.FRANCE )
            .withZone( ZoneId.of("UTC+2"));

        LocalDate localNow = LocalDate.now();
        Instant instantNow = Instant.now();
        Instant hour0 = Instant.from(formatter.parse(localNow + " 00:00:00"));
        Instant hour08 = Instant.from(formatter.parse(localNow + " 08:00:00"));
        Instant hour13 = Instant.from(formatter.parse(localNow + " 13:00:00"));
        Instant hour12 = Instant.from(formatter.parse(localNow + " 12:00:00"));
        Instant hour18 = Instant.from(formatter.parse(localNow + " 18:00:00"));

        if(instantNow.isAfter(hour08) && instantNow.isBefore(hour18)) {
            List<Badgeage> listBadgeage = badgeageRepository.findByUtilisateurAndCurrentDate(utilisateurService.getCurrentUtilisateur().get(), localNow);

            if (instantNow.isAfter(hour08) && instantNow.isBefore(hour12)) {
                if (listBadgeage.get(0).getBadgeageEleve().equals(hour0)) {
                    listBadgeage.get(0).setBadgeageEleve(instantNow);
                    return badgeageRepository.save(listBadgeage.get(0));
                } else {
                    throw new CustomParameterizedException("AlreadyDone");
                }
            }

            if (instantNow.isAfter(hour13) && instantNow.isBefore(hour18)) {
                if (listBadgeage.get(1).getBadgeageEleve().equals(hour0)) {
                    System.out.println("4");
                    listBadgeage.get(1).setBadgeageEleve(instantNow);
                    return badgeageRepository.save(listBadgeage.get(1));
                } else {
                    throw new CustomParameterizedException("AlreadyDone");
                }
            }
        }

        throw new CustomParameterizedException("OutOfOpeningTime");
    }
}
