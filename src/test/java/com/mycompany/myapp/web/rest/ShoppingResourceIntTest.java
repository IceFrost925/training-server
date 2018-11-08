package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TrainingApp;

import com.mycompany.myapp.domain.Shopping;
import com.mycompany.myapp.repository.ShoppingRepository;
import com.mycompany.myapp.service.ShoppingService;
import com.mycompany.myapp.service.dto.ShoppingDTO;
import com.mycompany.myapp.service.mapper.ShoppingMapper;
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
 * Test class for the ShoppingResource REST controller.
 *
 * @see ShoppingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class ShoppingResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_2 = "BBBBBBBBBB";

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private ShoppingMapper shoppingMapper;

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShoppingMockMvc;

    private Shopping shopping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShoppingResource shoppingResource = new ShoppingResource(shoppingService);
        this.restShoppingMockMvc = MockMvcBuilders.standaloneSetup(shoppingResource)
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
    public static Shopping createEntity(EntityManager em) {
        Shopping shopping = new Shopping()
            .number(DEFAULT_NUMBER)
            .extra1(DEFAULT_EXTRA_1)
            .extra2(DEFAULT_EXTRA_2);
        return shopping;
    }

    @Before
    public void initTest() {
        shopping = createEntity(em);
    }

    @Test
    @Transactional
    public void createShopping() throws Exception {
        int databaseSizeBeforeCreate = shoppingRepository.findAll().size();

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);
        restShoppingMockMvc.perform(post("/api/shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isCreated());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeCreate + 1);
        Shopping testShopping = shoppingList.get(shoppingList.size() - 1);
        assertThat(testShopping.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testShopping.getExtra1()).isEqualTo(DEFAULT_EXTRA_1);
        assertThat(testShopping.getExtra2()).isEqualTo(DEFAULT_EXTRA_2);
    }

    @Test
    @Transactional
    public void createShoppingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingRepository.findAll().size();

        // Create the Shopping with an existing ID
        shopping.setId(1L);
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingMockMvc.perform(post("/api/shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShoppings() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        // Get all the shoppingList
        restShoppingMockMvc.perform(get("/api/shoppings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shopping.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].extra1").value(hasItem(DEFAULT_EXTRA_1.toString())))
            .andExpect(jsonPath("$.[*].extra2").value(hasItem(DEFAULT_EXTRA_2.toString())));
    }

    @Test
    @Transactional
    public void getShopping() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        // Get the shopping
        restShoppingMockMvc.perform(get("/api/shoppings/{id}", shopping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shopping.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.extra1").value(DEFAULT_EXTRA_1.toString()))
            .andExpect(jsonPath("$.extra2").value(DEFAULT_EXTRA_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShopping() throws Exception {
        // Get the shopping
        restShoppingMockMvc.perform(get("/api/shoppings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShopping() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();

        // Update the shopping
        Shopping updatedShopping = shoppingRepository.findOne(shopping.getId());
        // Disconnect from session so that the updates on updatedShopping are not directly saved in db
        em.detach(updatedShopping);
        updatedShopping
            .number(UPDATED_NUMBER)
            .extra1(UPDATED_EXTRA_1)
            .extra2(UPDATED_EXTRA_2);
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(updatedShopping);

        restShoppingMockMvc.perform(put("/api/shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isOk());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
        Shopping testShopping = shoppingList.get(shoppingList.size() - 1);
        assertThat(testShopping.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testShopping.getExtra1()).isEqualTo(UPDATED_EXTRA_1);
        assertThat(testShopping.getExtra2()).isEqualTo(UPDATED_EXTRA_2);
    }

    @Test
    @Transactional
    public void updateNonExistingShopping() throws Exception {
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShoppingMockMvc.perform(put("/api/shoppings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isCreated());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShopping() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);
        int databaseSizeBeforeDelete = shoppingRepository.findAll().size();

        // Get the shopping
        restShoppingMockMvc.perform(delete("/api/shoppings/{id}", shopping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shopping.class);
        Shopping shopping1 = new Shopping();
        shopping1.setId(1L);
        Shopping shopping2 = new Shopping();
        shopping2.setId(shopping1.getId());
        assertThat(shopping1).isEqualTo(shopping2);
        shopping2.setId(2L);
        assertThat(shopping1).isNotEqualTo(shopping2);
        shopping1.setId(null);
        assertThat(shopping1).isNotEqualTo(shopping2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingDTO.class);
        ShoppingDTO shoppingDTO1 = new ShoppingDTO();
        shoppingDTO1.setId(1L);
        ShoppingDTO shoppingDTO2 = new ShoppingDTO();
        assertThat(shoppingDTO1).isNotEqualTo(shoppingDTO2);
        shoppingDTO2.setId(shoppingDTO1.getId());
        assertThat(shoppingDTO1).isEqualTo(shoppingDTO2);
        shoppingDTO2.setId(2L);
        assertThat(shoppingDTO1).isNotEqualTo(shoppingDTO2);
        shoppingDTO1.setId(null);
        assertThat(shoppingDTO1).isNotEqualTo(shoppingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shoppingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shoppingMapper.fromId(null)).isNull();
    }
}
