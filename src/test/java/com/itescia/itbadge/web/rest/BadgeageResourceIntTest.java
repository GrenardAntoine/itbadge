package com.itescia.itbadge.web.rest;

import com.itescia.itbadge.ItbadgeApp;

import com.itescia.itbadge.domain.Badgeage;
import com.itescia.itbadge.repository.BadgeageRepository;
import com.itescia.itbadge.service.BadgeageService;
import com.itescia.itbadge.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.itescia.itbadge.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BadgeageResource REST controller.
 *
 * @see BadgeageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItbadgeApp.class)
public class BadgeageResourceIntTest {

    private static final LocalDate DEFAULT_CURRENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CURRENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_BADGEAGE_ELEVE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BADGEAGE_ELEVE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BADGEAGE_CORRIGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BADGEAGE_CORRIGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BadgeageRepository badgeageRepository;

    

    @Autowired
    private BadgeageService badgeageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBadgeageMockMvc;

    private Badgeage badgeage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BadgeageResource badgeageResource = new BadgeageResource(badgeageService);
        this.restBadgeageMockMvc = MockMvcBuilders.standaloneSetup(badgeageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badgeage createEntity(EntityManager em) {
        Badgeage badgeage = new Badgeage()
            .currentDate(DEFAULT_CURRENT_DATE)
            .badgeageEleve(DEFAULT_BADGEAGE_ELEVE)
            .badgeageCorrige(DEFAULT_BADGEAGE_CORRIGE);
        return badgeage;
    }

    @Before
    public void initTest() {
        badgeage = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadgeage() throws Exception {
        int databaseSizeBeforeCreate = badgeageRepository.findAll().size();

        // Create the Badgeage
        restBadgeageMockMvc.perform(post("/api/badgeages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeage)))
            .andExpect(status().isCreated());

        // Validate the Badgeage in the database
        List<Badgeage> badgeageList = badgeageRepository.findAll();
        assertThat(badgeageList).hasSize(databaseSizeBeforeCreate + 1);
        Badgeage testBadgeage = badgeageList.get(badgeageList.size() - 1);
        assertThat(testBadgeage.getCurrentDate()).isEqualTo(DEFAULT_CURRENT_DATE);
        assertThat(testBadgeage.getBadgeageEleve()).isEqualTo(DEFAULT_BADGEAGE_ELEVE);
        assertThat(testBadgeage.getBadgeageCorrige()).isEqualTo(DEFAULT_BADGEAGE_CORRIGE);
    }

    @Test
    @Transactional
    public void createBadgeageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgeageRepository.findAll().size();

        // Create the Badgeage with an existing ID
        badgeage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeageMockMvc.perform(post("/api/badgeages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeage)))
            .andExpect(status().isBadRequest());

        // Validate the Badgeage in the database
        List<Badgeage> badgeageList = badgeageRepository.findAll();
        assertThat(badgeageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCurrentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeageRepository.findAll().size();
        // set the field null
        badgeage.setCurrentDate(null);

        // Create the Badgeage, which fails.

        restBadgeageMockMvc.perform(post("/api/badgeages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeage)))
            .andExpect(status().isBadRequest());

        List<Badgeage> badgeageList = badgeageRepository.findAll();
        assertThat(badgeageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBadgeages() throws Exception {
        // Initialize the database
        badgeageRepository.saveAndFlush(badgeage);

        // Get all the badgeageList
        restBadgeageMockMvc.perform(get("/api/badgeages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgeage.getId().intValue())))
            .andExpect(jsonPath("$.[*].currentDate").value(hasItem(DEFAULT_CURRENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].badgeageEleve").value(hasItem(DEFAULT_BADGEAGE_ELEVE.toString())))
            .andExpect(jsonPath("$.[*].badgeageCorrige").value(hasItem(DEFAULT_BADGEAGE_CORRIGE.toString())));
    }
    

    @Test
    @Transactional
    public void getBadgeage() throws Exception {
        // Initialize the database
        badgeageRepository.saveAndFlush(badgeage);

        // Get the badgeage
        restBadgeageMockMvc.perform(get("/api/badgeages/{id}", badgeage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(badgeage.getId().intValue()))
            .andExpect(jsonPath("$.currentDate").value(DEFAULT_CURRENT_DATE.toString()))
            .andExpect(jsonPath("$.badgeageEleve").value(DEFAULT_BADGEAGE_ELEVE.toString()))
            .andExpect(jsonPath("$.badgeageCorrige").value(DEFAULT_BADGEAGE_CORRIGE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBadgeage() throws Exception {
        // Get the badgeage
        restBadgeageMockMvc.perform(get("/api/badgeages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadgeage() throws Exception {
        // Initialize the database
        badgeageService.save(badgeage);

        int databaseSizeBeforeUpdate = badgeageRepository.findAll().size();

        // Update the badgeage
        Badgeage updatedBadgeage = badgeageRepository.findById(badgeage.getId()).get();
        // Disconnect from session so that the updates on updatedBadgeage are not directly saved in db
        em.detach(updatedBadgeage);
        updatedBadgeage
            .currentDate(UPDATED_CURRENT_DATE)
            .badgeageEleve(UPDATED_BADGEAGE_ELEVE)
            .badgeageCorrige(UPDATED_BADGEAGE_CORRIGE);

        restBadgeageMockMvc.perform(put("/api/badgeages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBadgeage)))
            .andExpect(status().isOk());

        // Validate the Badgeage in the database
        List<Badgeage> badgeageList = badgeageRepository.findAll();
        assertThat(badgeageList).hasSize(databaseSizeBeforeUpdate);
        Badgeage testBadgeage = badgeageList.get(badgeageList.size() - 1);
        assertThat(testBadgeage.getCurrentDate()).isEqualTo(UPDATED_CURRENT_DATE);
        assertThat(testBadgeage.getBadgeageEleve()).isEqualTo(UPDATED_BADGEAGE_ELEVE);
        assertThat(testBadgeage.getBadgeageCorrige()).isEqualTo(UPDATED_BADGEAGE_CORRIGE);
    }

    @Test
    @Transactional
    public void updateNonExistingBadgeage() throws Exception {
        int databaseSizeBeforeUpdate = badgeageRepository.findAll().size();

        // Create the Badgeage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBadgeageMockMvc.perform(put("/api/badgeages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeage)))
            .andExpect(status().isBadRequest());

        // Validate the Badgeage in the database
        List<Badgeage> badgeageList = badgeageRepository.findAll();
        assertThat(badgeageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBadgeage() throws Exception {
        // Initialize the database
        badgeageService.save(badgeage);

        int databaseSizeBeforeDelete = badgeageRepository.findAll().size();

        // Get the badgeage
        restBadgeageMockMvc.perform(delete("/api/badgeages/{id}", badgeage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Badgeage> badgeageList = badgeageRepository.findAll();
        assertThat(badgeageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Badgeage.class);
        Badgeage badgeage1 = new Badgeage();
        badgeage1.setId(1L);
        Badgeage badgeage2 = new Badgeage();
        badgeage2.setId(badgeage1.getId());
        assertThat(badgeage1).isEqualTo(badgeage2);
        badgeage2.setId(2L);
        assertThat(badgeage1).isNotEqualTo(badgeage2);
        badgeage1.setId(null);
        assertThat(badgeage1).isNotEqualTo(badgeage2);
    }
}
