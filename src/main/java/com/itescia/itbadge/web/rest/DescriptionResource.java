package com.itescia.itbadge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itescia.itbadge.domain.Description;
import com.itescia.itbadge.service.DescriptionService;
import com.itescia.itbadge.web.rest.errors.BadRequestAlertException;
import com.itescia.itbadge.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Description.
 */
@RestController
@RequestMapping("/api")
public class DescriptionResource {

    private final Logger log = LoggerFactory.getLogger(DescriptionResource.class);

    private static final String ENTITY_NAME = "description";

    private final DescriptionService descriptionService;

    public DescriptionResource(DescriptionService descriptionService) {
        this.descriptionService = descriptionService;
    }

    /**
     * POST  /descriptions : Create a new description.
     *
     * @param description the description to create
     * @return the ResponseEntity with status 201 (Created) and with body the new description, or with status 400 (Bad Request) if the description has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/descriptions")
    @Timed
    public ResponseEntity<Description> createDescription(@Valid @RequestBody Description description) throws URISyntaxException {
        log.debug("REST request to save Description : {}", description);
        if (description.getId() != null) {
            throw new BadRequestAlertException("A new description cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Description result = descriptionService.save(description);
        return ResponseEntity.created(new URI("/api/descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /descriptions : Updates an existing description.
     *
     * @param description the description to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated description,
     * or with status 400 (Bad Request) if the description is not valid,
     * or with status 500 (Internal Server Error) if the description couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/descriptions")
    @Timed
    public ResponseEntity<Description> updateDescription(@Valid @RequestBody Description description) throws URISyntaxException {
        log.debug("REST request to update Description : {}", description);
        if (description.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Description result = descriptionService.save(description);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, description.getId().toString()))
            .body(result);
    }

    /**
     * GET  /descriptions : get all the descriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of descriptions in body
     */
    @GetMapping("/descriptions")
    @Timed
    public List<Description> getAllDescriptions() {
        log.debug("REST request to get all Descriptions");
        return descriptionService.findAll();
    }

    /**
     * GET  /descriptions/:id : get the "id" description.
     *
     * @param id the id of the description to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the description, or with status 404 (Not Found)
     */
    @GetMapping("/descriptions/{id}")
    @Timed
    public ResponseEntity<Description> getDescription(@PathVariable Long id) {
        log.debug("REST request to get Description : {}", id);
        Optional<Description> description = descriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(description);
    }

    /**
     * DELETE  /descriptions/:id : delete the "id" description.
     *
     * @param id the id of the description to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/descriptions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDescription(@PathVariable Long id) {
        log.debug("REST request to delete Description : {}", id);
        descriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
