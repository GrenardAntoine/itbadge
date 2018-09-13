package com.itescia.itbadge.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itescia.itbadge.domain.Groupe;
import com.itescia.itbadge.service.GroupeService;
import com.itescia.itbadge.service.UploadService;
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
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Groupe.
 */
@RestController
@RequestMapping("/api")
public class UploadResource {

    private final Logger log = LoggerFactory.getLogger(UploadResource.class);

    private final UploadService uploadService;

    public UploadResource(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    /**
     * POST  /upload : Upload new file
     *
     */
    @PostMapping("/upload")
    @Timed
    public ResponseEntity<Void> uploadFile(@Valid @RequestBody File file) throws URISyntaxException {
        log.debug("REST request to save Groupe : {}", file.getName());

        uploadService.uploadFile(file);

        return ResponseEntity.ok().build();

    }
}
