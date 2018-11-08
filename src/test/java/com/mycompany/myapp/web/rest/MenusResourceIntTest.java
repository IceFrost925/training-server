package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TrainingApp;

import com.mycompany.myapp.domain.Menus;
import com.mycompany.myapp.repository.MenusRepository;
import com.mycompany.myapp.service.MenusService;
import com.mycompany.myapp.service.dto.MenusDTO;
import com.mycompany.myapp.service.mapper.MenusMapper;
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
 * Test class for the MenusResource REST controller.
 *
 * @see MenusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class MenusResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND = "AAAAAAAAAA";
    private static final String UPDATED_SECOND = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_2 = "BBBBBBBBBB";

    @Autowired
    private MenusRepository menusRepository;

    @Autowired
    private MenusMapper menusMapper;

    @Autowired
    private MenusService menusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMenusMockMvc;

    private Menus menus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenusResource menusResource = new MenusResource(menusService);
        this.restMenusMockMvc = MockMvcBuilders.standaloneSetup(menusResource)
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
    public static Menus createEntity(EntityManager em) {
        Menus menus = new Menus()
            .type(DEFAULT_TYPE)
            .second(DEFAULT_SECOND)
            .extra1(DEFAULT_EXTRA_1)
            .extra2(DEFAULT_EXTRA_2);
        return menus;
    }

    @Before
    public void initTest() {
        menus = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenus() throws Exception {
        int databaseSizeBeforeCreate = menusRepository.findAll().size();

        // Create the Menus
        MenusDTO menusDTO = menusMapper.toDto(menus);
        restMenusMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menusDTO)))
            .andExpect(status().isCreated());

        // Validate the Menus in the database
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeCreate + 1);
        Menus testMenus = menusList.get(menusList.size() - 1);
        assertThat(testMenus.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMenus.getSecond()).isEqualTo(DEFAULT_SECOND);
        assertThat(testMenus.getExtra1()).isEqualTo(DEFAULT_EXTRA_1);
        assertThat(testMenus.getExtra2()).isEqualTo(DEFAULT_EXTRA_2);
    }

    @Test
    @Transactional
    public void createMenusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menusRepository.findAll().size();

        // Create the Menus with an existing ID
        menus.setId(1L);
        MenusDTO menusDTO = menusMapper.toDto(menus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenusMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Menus in the database
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMenus() throws Exception {
        // Initialize the database
        menusRepository.saveAndFlush(menus);

        // Get all the menusList
        restMenusMockMvc.perform(get("/api/menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menus.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].second").value(hasItem(DEFAULT_SECOND.toString())))
            .andExpect(jsonPath("$.[*].extra1").value(hasItem(DEFAULT_EXTRA_1.toString())))
            .andExpect(jsonPath("$.[*].extra2").value(hasItem(DEFAULT_EXTRA_2.toString())));
    }

    @Test
    @Transactional
    public void getMenus() throws Exception {
        // Initialize the database
        menusRepository.saveAndFlush(menus);

        // Get the menus
        restMenusMockMvc.perform(get("/api/menus/{id}", menus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menus.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.second").value(DEFAULT_SECOND.toString()))
            .andExpect(jsonPath("$.extra1").value(DEFAULT_EXTRA_1.toString()))
            .andExpect(jsonPath("$.extra2").value(DEFAULT_EXTRA_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMenus() throws Exception {
        // Get the menus
        restMenusMockMvc.perform(get("/api/menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenus() throws Exception {
        // Initialize the database
        menusRepository.saveAndFlush(menus);
        int databaseSizeBeforeUpdate = menusRepository.findAll().size();

        // Update the menus
        Menus updatedMenus = menusRepository.findOne(menus.getId());
        // Disconnect from session so that the updates on updatedMenus are not directly saved in db
        em.detach(updatedMenus);
        updatedMenus
            .type(UPDATED_TYPE)
            .second(UPDATED_SECOND)
            .extra1(UPDATED_EXTRA_1)
            .extra2(UPDATED_EXTRA_2);
        MenusDTO menusDTO = menusMapper.toDto(updatedMenus);

        restMenusMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menusDTO)))
            .andExpect(status().isOk());

        // Validate the Menus in the database
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeUpdate);
        Menus testMenus = menusList.get(menusList.size() - 1);
        assertThat(testMenus.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMenus.getSecond()).isEqualTo(UPDATED_SECOND);
        assertThat(testMenus.getExtra1()).isEqualTo(UPDATED_EXTRA_1);
        assertThat(testMenus.getExtra2()).isEqualTo(UPDATED_EXTRA_2);
    }

    @Test
    @Transactional
    public void updateNonExistingMenus() throws Exception {
        int databaseSizeBeforeUpdate = menusRepository.findAll().size();

        // Create the Menus
        MenusDTO menusDTO = menusMapper.toDto(menus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMenusMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menusDTO)))
            .andExpect(status().isCreated());

        // Validate the Menus in the database
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMenus() throws Exception {
        // Initialize the database
        menusRepository.saveAndFlush(menus);
        int databaseSizeBeforeDelete = menusRepository.findAll().size();

        // Get the menus
        restMenusMockMvc.perform(delete("/api/menus/{id}", menus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Menus> menusList = menusRepository.findAll();
        assertThat(menusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Menus.class);
        Menus menus1 = new Menus();
        menus1.setId(1L);
        Menus menus2 = new Menus();
        menus2.setId(menus1.getId());
        assertThat(menus1).isEqualTo(menus2);
        menus2.setId(2L);
        assertThat(menus1).isNotEqualTo(menus2);
        menus1.setId(null);
        assertThat(menus1).isNotEqualTo(menus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenusDTO.class);
        MenusDTO menusDTO1 = new MenusDTO();
        menusDTO1.setId(1L);
        MenusDTO menusDTO2 = new MenusDTO();
        assertThat(menusDTO1).isNotEqualTo(menusDTO2);
        menusDTO2.setId(menusDTO1.getId());
        assertThat(menusDTO1).isEqualTo(menusDTO2);
        menusDTO2.setId(2L);
        assertThat(menusDTO1).isNotEqualTo(menusDTO2);
        menusDTO1.setId(null);
        assertThat(menusDTO1).isNotEqualTo(menusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(menusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(menusMapper.fromId(null)).isNull();
    }
}
