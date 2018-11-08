package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TrainingApp;

import com.mycompany.myapp.domain.Appraise;
import com.mycompany.myapp.repository.AppraiseRepository;
import com.mycompany.myapp.service.AppraiseService;
import com.mycompany.myapp.service.dto.AppraiseDTO;
import com.mycompany.myapp.service.mapper.AppraiseMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AppraiseResource REST controller.
 *
 * @see AppraiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class AppraiseResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_STAR = 1;
    private static final Integer UPDATED_STAR = 2;

    private static final String DEFAULT_EXTRA_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_2 = "BBBBBBBBBB";

    @Autowired
    private AppraiseRepository appraiseRepository;

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Autowired
    private AppraiseService appraiseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppraiseMockMvc;

    private Appraise appraise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppraiseResource appraiseResource = new AppraiseResource(appraiseService);
        this.restAppraiseMockMvc = MockMvcBuilders.standaloneSetup(appraiseResource)
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
    public static Appraise createEntity(EntityManager em) {
        Appraise appraise = new Appraise()
            .content(DEFAULT_CONTENT)
            .star(DEFAULT_STAR)
            .extra1(DEFAULT_EXTRA_1)
            .extra2(DEFAULT_EXTRA_2);
        return appraise;
    }

    @Before
    public void initTest() {
        appraise = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppraise() throws Exception {
        int databaseSizeBeforeCreate = appraiseRepository.findAll().size();

        // Create the Appraise
        AppraiseDTO appraiseDTO = appraiseMapper.toDto(appraise);
        restAppraiseMockMvc.perform(post("/api/appraises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appraiseDTO)))
            .andExpect(status().isCreated());

        // Validate the Appraise in the database
        List<Appraise> appraiseList = appraiseRepository.findAll();
        assertThat(appraiseList).hasSize(databaseSizeBeforeCreate + 1);
        Appraise testAppraise = appraiseList.get(appraiseList.size() - 1);
        assertThat(testAppraise.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAppraise.getStar()).isEqualTo(DEFAULT_STAR);
        assertThat(testAppraise.getExtra1()).isEqualTo(DEFAULT_EXTRA_1);
        assertThat(testAppraise.getExtra2()).isEqualTo(DEFAULT_EXTRA_2);
    }

    @Test
    @Transactional
    public void createAppraiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appraiseRepository.findAll().size();

        // Create the Appraise with an existing ID
        appraise.setId(1L);
        AppraiseDTO appraiseDTO = appraiseMapper.toDto(appraise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppraiseMockMvc.perform(post("/api/appraises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appraiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Appraise in the database
        List<Appraise> appraiseList = appraiseRepository.findAll();
        assertThat(appraiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAppraises() throws Exception {
        // Initialize the database
        appraiseRepository.saveAndFlush(appraise);

        // Get all the appraiseList
        restAppraiseMockMvc.perform(get("/api/appraises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appraise.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].star").value(hasItem(DEFAULT_STAR)))
            .andExpect(jsonPath("$.[*].extra1").value(hasItem(DEFAULT_EXTRA_1.toString())))
            .andExpect(jsonPath("$.[*].extra2").value(hasItem(DEFAULT_EXTRA_2.toString())));
    }

    @Test
    @Transactional
    public void getAppraise() throws Exception {
        // Initialize the database
        appraiseRepository.saveAndFlush(appraise);

        // Get the appraise
        restAppraiseMockMvc.perform(get("/api/appraises/{id}", appraise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appraise.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.star").value(DEFAULT_STAR))
            .andExpect(jsonPath("$.extra1").value(DEFAULT_EXTRA_1.toString()))
            .andExpect(jsonPath("$.extra2").value(DEFAULT_EXTRA_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppraise() throws Exception {
        // Get the appraise
        restAppraiseMockMvc.perform(get("/api/appraises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppraise() throws Exception {
        // Initialize the database
        appraiseRepository.saveAndFlush(appraise);
        int databaseSizeBeforeUpdate = appraiseRepository.findAll().size();

        // Update the appraise
        Appraise updatedAppraise = appraiseRepository.findOne(appraise.getId());
        // Disconnect from session so that the updates on updatedAppraise are not directly saved in db
        em.detach(updatedAppraise);
        updatedAppraise
            .content(UPDATED_CONTENT)
            .star(UPDATED_STAR)
            .extra1(UPDATED_EXTRA_1)
            .extra2(UPDATED_EXTRA_2);
        AppraiseDTO appraiseDTO = appraiseMapper.toDto(updatedAppraise);

        restAppraiseMockMvc.perform(put("/api/appraises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appraiseDTO)))
            .andExpect(status().isOk());

        // Validate the Appraise in the database
        List<Appraise> appraiseList = appraiseRepository.findAll();
        assertThat(appraiseList).hasSize(databaseSizeBeforeUpdate);
        Appraise testAppraise = appraiseList.get(appraiseList.size() - 1);
        assertThat(testAppraise.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAppraise.getStar()).isEqualTo(UPDATED_STAR);
        assertThat(testAppraise.getExtra1()).isEqualTo(UPDATED_EXTRA_1);
        assertThat(testAppraise.getExtra2()).isEqualTo(UPDATED_EXTRA_2);
    }

    @Test
    @Transactional
    public void updateNonExistingAppraise() throws Exception {
        int databaseSizeBeforeUpdate = appraiseRepository.findAll().size();

        // Create the Appraise
        AppraiseDTO appraiseDTO = appraiseMapper.toDto(appraise);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppraiseMockMvc.perform(put("/api/appraises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appraiseDTO)))
            .andExpect(status().isCreated());

        // Validate the Appraise in the database
        List<Appraise> appraiseList = appraiseRepository.findAll();
        assertThat(appraiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppraise() throws Exception {
        // Initialize the database
        appraiseRepository.saveAndFlush(appraise);
        int databaseSizeBeforeDelete = appraiseRepository.findAll().size();

        // Get the appraise
        restAppraiseMockMvc.perform(delete("/api/appraises/{id}", appraise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Appraise> appraiseList = appraiseRepository.findAll();
        assertThat(appraiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appraise.class);
        Appraise appraise1 = new Appraise();
        appraise1.setId(1L);
        Appraise appraise2 = new Appraise();
        appraise2.setId(appraise1.getId());
        assertThat(appraise1).isEqualTo(appraise2);
        appraise2.setId(2L);
        assertThat(appraise1).isNotEqualTo(appraise2);
        appraise1.setId(null);
        assertThat(appraise1).isNotEqualTo(appraise2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppraiseDTO.class);
        AppraiseDTO appraiseDTO1 = new AppraiseDTO();
        appraiseDTO1.setId(1L);
        AppraiseDTO appraiseDTO2 = new AppraiseDTO();
        assertThat(appraiseDTO1).isNotEqualTo(appraiseDTO2);
        appraiseDTO2.setId(appraiseDTO1.getId());
        assertThat(appraiseDTO1).isEqualTo(appraiseDTO2);
        appraiseDTO2.setId(2L);
        assertThat(appraiseDTO1).isNotEqualTo(appraiseDTO2);
        appraiseDTO1.setId(null);
        assertThat(appraiseDTO1).isNotEqualTo(appraiseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(appraiseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(appraiseMapper.fromId(null)).isNull();
    }
}
