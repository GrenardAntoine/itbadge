package com.itescia.itbadge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itescia.itbadge.domain.Badgeage;
import com.itescia.itbadge.domain.Utilisateur;
import com.itescia.itbadge.service.BadgeageService;
import com.itescia.itbadge.service.UtilisateurService;
import com.itescia.itbadge.web.rest.errors.BadRequestAlertException;
import com.itescia.itbadge.web.rest.util.HeaderUtil;
import com.itescia.itbadge.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Badgeage.
 */
@RestController
@RequestMapping("/api")
public class BadgeageResource {

    private final Logger log = LoggerFactory.getLogger(BadgeageResource.class);

    private static final String ENTITY_NAME = "badgeage";

    private final BadgeageService badgeageService;
    private final UtilisateurService utilisateurService;

    public BadgeageResource(BadgeageService badgeageService, UtilisateurService utilisateurService) {
        this.badgeageService = badgeageService;
        this.utilisateurService = utilisateurService;
    }

    /**
     * POST  /badgeages : Create a new badgeage.
     *
     * @param badgeage the badgeage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new badgeage, or with status 400 (Bad Request) if the badgeage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/badgeages")
    @Timed
    public ResponseEntity<Badgeage> createBadgeage(@Valid @RequestBody Badgeage badgeage) throws URISyntaxException {
        log.debug("REST request to save Badgeage : {}", badgeage);
        if (badgeage.getId() != null) {
            throw new BadRequestAlertException("A new badgeage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Badgeage result = badgeageService.save(badgeage);
        return ResponseEntity.created(new URI("/api/badgeages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /badgeages : Updates an existing badgeage.
     *
     * @param badgeage the badgeage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated badgeage,
     * or with status 400 (Bad Request) if the badgeage is not valid,
     * or with status 500 (Internal Server Error) if the badgeage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/badgeages")
    @Timed
    public ResponseEntity<Badgeage> updateBadgeage(@Valid @RequestBody Badgeage badgeage) throws URISyntaxException {
        log.debug("REST request to update Badgeage : {}", badgeage);
        if (badgeage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Badgeage result = badgeageService.save(badgeage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, badgeage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /badgeages : get all the badgeages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of badgeages in body
     */
    @GetMapping("/badgeages")
    @Timed
    public ResponseEntity<List<Badgeage>> getAllBadgeages(Pageable pageable) {
        log.debug("REST request to get a page of Badgeages");
        Page<Badgeage> page = badgeageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/badgeages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /badgeages/:id : get the "id" badgeage.
     *
     * @param id the id of the badgeage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the badgeage, or with status 404 (Not Found)
     */
    @GetMapping("/badgeages/{id}")
    @Timed
    public ResponseEntity<Badgeage> getBadgeage(@PathVariable Long id) {
        log.debug("REST request to get Badgeage : {}", id);
        Optional<Badgeage> badgeage = badgeageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeage);
    }

    /**
     * DELETE  /badgeages/:id : delete the "id" badgeage.
     *
     * @param id the id of the badgeage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/badgeages/{id}")
    @Timed
    public ResponseEntity<Void> deleteBadgeage(@PathVariable Long id) {
        log.debug("REST request to delete Badgeage : {}", id);
        badgeageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }



    @GetMapping("/badgeages/current")
    @Timed
    public ResponseEntity<List<Badgeage>> getAllBadgeagesByCurrentUtilisateur(Pageable pageable) {
        log.debug("REST request to get a page of Badgeages by current utilisateur");
        Page<Badgeage> page = badgeageService.findByUtilisateur(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/badgeages/current");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/badgeages/currentDate")
    @Timed
    public ResponseEntity<List<Badgeage>> getAllBadgeagesByCurrentUtilisateurDate(Pageable pageable) {
        log.debug("REST request to get a page of Badgeages by current utilisateur");
        Page<Badgeage> page = badgeageService.findByUtilisateurAndCurrentDate(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/badgeages/current");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/badgeages/allByCurrentCours")
    @Timed
    public ResponseEntity<List<Badgeage>> getAllBadgeagesByCurrentGroupeDate() {
        log.debug("REST request to get a page of Badgeages by current utilisateur");
        List<Utilisateur> listStudent = new ArrayList<>();
        listStudent = utilisateurService.findStudent();
        List<Badgeage> listBadgeage = new ArrayList<Badgeage>();
        for(int i=0; i<listStudent.size(); i++) {
            listBadgeage.add(badgeageService.findByUtilisateur(listStudent.get(i)).get());
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(null, "/api/badgeages");

        return new ResponseEntity<>(listBadgeage,headers, HttpStatus.OK);
    }

    @PostMapping("/badgeages/addBadgageUser")
    @Timed
    public ResponseEntity<Badgeage> createBadgeageUser(@Valid @RequestBody Badgeage badgeage) throws URISyntaxException {
        log.debug("REST request to save Badgeage : {}", badgeage);
        if (badgeage.getId() != null) {
            throw new BadRequestAlertException("A new badgeage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        badgeage.setUtilisateur(utilisateurService.getCurrentUtilisateur().get());
        Badgeage result = badgeageService.save(badgeage);
        return ResponseEntity.created(new URI("/api/badgeages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
