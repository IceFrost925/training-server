package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TrainingApp;

import com.mycompany.myapp.domain.SUser;
import com.mycompany.myapp.repository.SUserRepository;
import com.mycompany.myapp.service.SUserService;
import com.mycompany.myapp.service.dto.SUserDTO;
import com.mycompany.myapp.service.mapper.SUserMapper;
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
 * Test class for the SUserResource REST controller.
 *
 * @see SUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class SUserResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWD = "BBBBBBBBBB";

    private static final String DEFAULT_TELL = "AAAAAAAAAA";
    private static final String UPDATED_TELL = "BBBBBBBBBB";

    private static final Integer DEFAULT_INTEGRAL = 1;
    private static final Integer UPDATED_INTEGRAL = 2;

    private static final String DEFAULT_EXTRA_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_2 = "BBBBBBBBBB";

    @Autowired
    private SUserRepository sUserRepository;

    @Autowired
    private SUserMapper sUserMapper;

    @Autowired
    private SUserService sUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSUserMockMvc;

    private SUser sUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SUserResource sUserResource = new SUserResource(sUserService);
        this.restSUserMockMvc = MockMvcBuilders.standaloneSetup(sUserResource)
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
    public static SUser createEntity(EntityManager em) {
        SUser sUser = new SUser()
            .username(DEFAULT_USERNAME)
            .image(DEFAULT_IMAGE)
            .email(DEFAULT_EMAIL)
            .passwd(DEFAULT_PASSWD)
            .tell(DEFAULT_TELL)
            .integral(DEFAULT_INTEGRAL)
            .extra1(DEFAULT_EXTRA_1)
            .extra2(DEFAULT_EXTRA_2);
        return sUser;
    }

    @Before
    public void initTest() {
        sUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createSUser() throws Exception {
        int databaseSizeBeforeCreate = sUserRepository.findAll().size();

        // Create the SUser
        SUserDTO sUserDTO = sUserMapper.toDto(sUser);
        restSUserMockMvc.perform(post("/api/s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sUserDTO)))
            .andExpect(status().isCreated());

        // Validate the SUser in the database
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeCreate + 1);
        SUser testSUser = sUserList.get(sUserList.size() - 1);
        assertThat(testSUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSUser.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSUser.getPasswd()).isEqualTo(DEFAULT_PASSWD);
        assertThat(testSUser.getTell()).isEqualTo(DEFAULT_TELL);
        assertThat(testSUser.getIntegral()).isEqualTo(DEFAULT_INTEGRAL);
        assertThat(testSUser.getExtra1()).isEqualTo(DEFAULT_EXTRA_1);
        assertThat(testSUser.getExtra2()).isEqualTo(DEFAULT_EXTRA_2);
    }

    @Test
    @Transactional
    public void createSUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sUserRepository.findAll().size();

        // Create the SUser with an existing ID
        sUser.setId(1L);
        SUserDTO sUserDTO = sUserMapper.toDto(sUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSUserMockMvc.perform(post("/api/s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SUser in the database
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSUsers() throws Exception {
        // Initialize the database
        sUserRepository.saveAndFlush(sUser);

        // Get all the sUserList
        restSUserMockMvc.perform(get("/api/s-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].passwd").value(hasItem(DEFAULT_PASSWD.toString())))
            .andExpect(jsonPath("$.[*].tell").value(hasItem(DEFAULT_TELL.toString())))
            .andExpect(jsonPath("$.[*].integral").value(hasItem(DEFAULT_INTEGRAL)))
            .andExpect(jsonPath("$.[*].extra1").value(hasItem(DEFAULT_EXTRA_1.toString())))
            .andExpect(jsonPath("$.[*].extra2").value(hasItem(DEFAULT_EXTRA_2.toString())));
    }

    @Test
    @Transactional
    public void getSUser() throws Exception {
        // Initialize the database
        sUserRepository.saveAndFlush(sUser);

        // Get the sUser
        restSUserMockMvc.perform(get("/api/s-users/{id}", sUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.passwd").value(DEFAULT_PASSWD.toString()))
            .andExpect(jsonPath("$.tell").value(DEFAULT_TELL.toString()))
            .andExpect(jsonPath("$.integral").value(DEFAULT_INTEGRAL))
            .andExpect(jsonPath("$.extra1").value(DEFAULT_EXTRA_1.toString()))
            .andExpect(jsonPath("$.extra2").value(DEFAULT_EXTRA_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSUser() throws Exception {
        // Get the sUser
        restSUserMockMvc.perform(get("/api/s-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSUser() throws Exception {
        // Initialize the database
        sUserRepository.saveAndFlush(sUser);
        int databaseSizeBeforeUpdate = sUserRepository.findAll().size();

        // Update the sUser
        SUser updatedSUser = sUserRepository.findOne(sUser.getId());
        // Disconnect from session so that the updates on updatedSUser are not directly saved in db
        em.detach(updatedSUser);
        updatedSUser
            .username(UPDATED_USERNAME)
            .image(UPDATED_IMAGE)
            .email(UPDATED_EMAIL)
            .passwd(UPDATED_PASSWD)
            .tell(UPDATED_TELL)
            .integral(UPDATED_INTEGRAL)
            .extra1(UPDATED_EXTRA_1)
            .extra2(UPDATED_EXTRA_2);
        SUserDTO sUserDTO = sUserMapper.toDto(updatedSUser);

        restSUserMockMvc.perform(put("/api/s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sUserDTO)))
            .andExpect(status().isOk());

        // Validate the SUser in the database
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeUpdate);
        SUser testSUser = sUserList.get(sUserList.size() - 1);
        assertThat(testSUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSUser.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSUser.getPasswd()).isEqualTo(UPDATED_PASSWD);
        assertThat(testSUser.getTell()).isEqualTo(UPDATED_TELL);
        assertThat(testSUser.getIntegral()).isEqualTo(UPDATED_INTEGRAL);
        assertThat(testSUser.getExtra1()).isEqualTo(UPDATED_EXTRA_1);
        assertThat(testSUser.getExtra2()).isEqualTo(UPDATED_EXTRA_2);
    }

    @Test
    @Transactional
    public void updateNonExistingSUser() throws Exception {
        int databaseSizeBeforeUpdate = sUserRepository.findAll().size();

        // Create the SUser
        SUserDTO sUserDTO = sUserMapper.toDto(sUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSUserMockMvc.perform(put("/api/s-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sUserDTO)))
            .andExpect(status().isCreated());

        // Validate the SUser in the database
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSUser() throws Exception {
        // Initialize the database
        sUserRepository.saveAndFlush(sUser);
        int databaseSizeBeforeDelete = sUserRepository.findAll().size();

        // Get the sUser
        restSUserMockMvc.perform(delete("/api/s-users/{id}", sUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SUser> sUserList = sUserRepository.findAll();
        assertThat(sUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SUser.class);
        SUser sUser1 = new SUser();
        sUser1.setId(1L);
        SUser sUser2 = new SUser();
        sUser2.setId(sUser1.getId());
        assertThat(sUser1).isEqualTo(sUser2);
        sUser2.setId(2L);
        assertThat(sUser1).isNotEqualTo(sUser2);
        sUser1.setId(null);
        assertThat(sUser1).isNotEqualTo(sUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SUserDTO.class);
        SUserDTO sUserDTO1 = new SUserDTO();
        sUserDTO1.setId(1L);
        SUserDTO sUserDTO2 = new SUserDTO();
        assertThat(sUserDTO1).isNotEqualTo(sUserDTO2);
        sUserDTO2.setId(sUserDTO1.getId());
        assertThat(sUserDTO1).isEqualTo(sUserDTO2);
        sUserDTO2.setId(2L);
        assertThat(sUserDTO1).isNotEqualTo(sUserDTO2);
        sUserDTO1.setId(null);
        assertThat(sUserDTO1).isNotEqualTo(sUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sUserMapper.fromId(null)).isNull();
    }
}
