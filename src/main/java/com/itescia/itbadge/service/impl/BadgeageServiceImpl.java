package com.itescia.itbadge.service.impl;

import com.itescia.itbadge.domain.Utilisateur;
import com.itescia.itbadge.service.BadgeageService;
import com.itescia.itbadge.domain.Badgeage;
import com.itescia.itbadge.repository.BadgeageRepository;
import com.itescia.itbadge.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
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


        LocalDate localNow = LocalDate.now();
        Instant instantNow = Instant.now();
        Instant hour0 = Instant.now().with(ChronoField.HOUR_OF_DAY, 0)
            .with(ChronoField.MINUTE_OF_HOUR, 0)
            .with(ChronoField.SECOND_OF_MINUTE, 0)
            .with(ChronoField.MILLI_OF_SECOND, 0);
        Instant hour08 = Instant.now().with(ChronoField.HOUR_OF_DAY, 8)
            .with(ChronoField.MINUTE_OF_HOUR, 0)
            .with(ChronoField.SECOND_OF_MINUTE, 0)
            .with(ChronoField.MILLI_OF_SECOND, 0);
        Instant hour13 = Instant.now().with(ChronoField.HOUR_OF_DAY, 13)
            .with(ChronoField.MINUTE_OF_HOUR, 0)
            .with(ChronoField.SECOND_OF_MINUTE, 0)
            .with(ChronoField.MILLI_OF_SECOND, 0);
        Instant hour12 = Instant.now().with(ChronoField.HOUR_OF_DAY, 12)
            .with(ChronoField.MINUTE_OF_HOUR, 0)
            .with(ChronoField.SECOND_OF_MINUTE, 0)
            .with(ChronoField.MILLI_OF_SECOND, 0);
        Instant hour18 = Instant.now().with(ChronoField.HOUR_OF_DAY, 18)
            .with(ChronoField.MINUTE_OF_HOUR, 0)
            .with(ChronoField.SECOND_OF_MINUTE, 0)
            .with(ChronoField.MILLI_OF_SECOND, 0);

        if(instantNow.isAfter(hour08) && instantNow.isBefore(hour18)) {
            List<Badgeage> listBadgeage = badgeageRepository.findByUtilisateurAndCurrentDate(utilisateurService.getCurrentUtilisateur().get(), localNow);

            if (instantNow.isAfter(hour08) && instantNow.isBefore(hour12)) {
                if (listBadgeage.get(0).getBadgeageEleve() == hour0) {
                    listBadgeage.get(0).setBadgeageEleve(instantNow);
                    badgeageRepository.save(listBadgeage.get(0));
                }
            }
            //TODO : Put 13 here
            if (instantNow.isAfter(hour12) && instantNow.isBefore(hour18)) {
                if (listBadgeage.get(1).getBadgeageEleve() == hour0) {
                    listBadgeage.get(1).setBadgeageEleve(instantNow);
                    badgeageRepository.save(listBadgeage.get(1));
                }
            }
        }
        return null;
    }
}
