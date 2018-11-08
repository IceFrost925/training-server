package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TrainingApp;

import com.mycompany.myapp.domain.Collect;
import com.mycompany.myapp.repository.CollectRepository;
import com.mycompany.myapp.service.CollectService;
import com.mycompany.myapp.service.dto.CollectDTO;
import com.mycompany.myapp.service.mapper.CollectMapper;
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
 * Test class for the CollectResource REST controller.
 *
 * @see CollectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class CollectResourceIntTest {

    private static final String DEFAULT_EXTRA_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_2 = "BBBBBBBBBB";

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private CollectService collectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCollectMockMvc;

    private Collect collect;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CollectResource collectResource = new CollectResource(collectService);
        this.restCollectMockMvc = MockMvcBuilders.standaloneSetup(collectResource)
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
    public static Collect createEntity(EntityManager em) {
        Collect collect = new Collect()
            .extra1(DEFAULT_EXTRA_1)
            .extra2(DEFAULT_EXTRA_2);
        return collect;
    }

    @Before
    public void initTest() {
        collect = createEntity(em);
    }

    @Test
    @Transactional
    public void createCollect() throws Exception {
        int databaseSizeBeforeCreate = collectRepository.findAll().size();

        // Create the Collect
        CollectDTO collectDTO = collectMapper.toDto(collect);
        restCollectMockMvc.perform(post("/api/collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectDTO)))
            .andExpect(status().isCreated());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeCreate + 1);
        Collect testCollect = collectList.get(collectList.size() - 1);
        assertThat(testCollect.getExtra1()).isEqualTo(DEFAULT_EXTRA_1);
        assertThat(testCollect.getExtra2()).isEqualTo(DEFAULT_EXTRA_2);
    }

    @Test
    @Transactional
    public void createCollectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = collectRepository.findAll().size();

        // Create the Collect with an existing ID
        collect.setId(1L);
        CollectDTO collectDTO = collectMapper.toDto(collect);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectMockMvc.perform(post("/api/collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCollects() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        // Get all the collectList
        restCollectMockMvc.perform(get("/api/collects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collect.getId().intValue())))
            .andExpect(jsonPath("$.[*].extra1").value(hasItem(DEFAULT_EXTRA_1.toString())))
            .andExpect(jsonPath("$.[*].extra2").value(hasItem(DEFAULT_EXTRA_2.toString())));
    }

    @Test
    @Transactional
    public void getCollect() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);

        // Get the collect
        restCollectMockMvc.perform(get("/api/collects/{id}", collect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(collect.getId().intValue()))
            .andExpect(jsonPath("$.extra1").value(DEFAULT_EXTRA_1.toString()))
            .andExpect(jsonPath("$.extra2").value(DEFAULT_EXTRA_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCollect() throws Exception {
        // Get the collect
        restCollectMockMvc.perform(get("/api/collects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCollect() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);
        int databaseSizeBeforeUpdate = collectRepository.findAll().size();

        // Update the collect
        Collect updatedCollect = collectRepository.findOne(collect.getId());
        // Disconnect from session so that the updates on updatedCollect are not directly saved in db
        em.detach(updatedCollect);
        updatedCollect
            .extra1(UPDATED_EXTRA_1)
            .extra2(UPDATED_EXTRA_2);
        CollectDTO collectDTO = collectMapper.toDto(updatedCollect);

        restCollectMockMvc.perform(put("/api/collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectDTO)))
            .andExpect(status().isOk());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate);
        Collect testCollect = collectList.get(collectList.size() - 1);
        assertThat(testCollect.getExtra1()).isEqualTo(UPDATED_EXTRA_1);
        assertThat(testCollect.getExtra2()).isEqualTo(UPDATED_EXTRA_2);
    }

    @Test
    @Transactional
    public void updateNonExistingCollect() throws Exception {
        int databaseSizeBeforeUpdate = collectRepository.findAll().size();

        // Create the Collect
        CollectDTO collectDTO = collectMapper.toDto(collect);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCollectMockMvc.perform(put("/api/collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(collectDTO)))
            .andExpect(status().isCreated());

        // Validate the Collect in the database
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCollect() throws Exception {
        // Initialize the database
        collectRepository.saveAndFlush(collect);
        int databaseSizeBeforeDelete = collectRepository.findAll().size();

        // Get the collect
        restCollectMockMvc.perform(delete("/api/collects/{id}", collect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Collect> collectList = collectRepository.findAll();
        assertThat(collectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collect.class);
        Collect collect1 = new Collect();
        collect1.setId(1L);
        Collect collect2 = new Collect();
        collect2.setId(collect1.getId());
        assertThat(collect1).isEqualTo(collect2);
        collect2.setId(2L);
        assertThat(collect1).isNotEqualTo(collect2);
        collect1.setId(null);
        assertThat(collect1).isNotEqualTo(collect2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectDTO.class);
        CollectDTO collectDTO1 = new CollectDTO();
        collectDTO1.setId(1L);
        CollectDTO collectDTO2 = new CollectDTO();
        assertThat(collectDTO1).isNotEqualTo(collectDTO2);
        collectDTO2.setId(collectDTO1.getId());
        assertThat(collectDTO1).isEqualTo(collectDTO2);
        collectDTO2.setId(2L);
        assertThat(collectDTO1).isNotEqualTo(collectDTO2);
        collectDTO1.setId(null);
        assertThat(collectDTO1).isNotEqualTo(collectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(collectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(collectMapper.fromId(null)).isNull();
    }
}
