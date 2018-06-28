package com.itescia.itbadge.service.impl;

import com.itescia.itbadge.service.DescriptionService;
import com.itescia.itbadge.domain.Description;
import com.itescia.itbadge.repository.DescriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Description.
 */
@Service
@Transactional
public class DescriptionServiceImpl implements DescriptionService {

    private final Logger log = LoggerFactory.getLogger(DescriptionServiceImpl.class);

    private final DescriptionRepository descriptionRepository;

    public DescriptionServiceImpl(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    /**
     * Save a description.
     *
     * @param description the entity to save
     * @return the persisted entity
     */
    @Override
    public Description save(Description description) {
        log.debug("Request to save Description : {}", description);        return descriptionRepository.save(description);
    }

    /**
     * Get all the descriptions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Description> findAll() {
        log.debug("Request to get all Descriptions");
        return descriptionRepository.findAll();
    }


    /**
     * Get one description by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Description> findOne(Long id) {
        log.debug("Request to get Description : {}", id);
        return descriptionRepository.findById(id);
    }

    /**
     * Delete the description by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Description : {}", id);
        descriptionRepository.deleteById(id);
    }
}
