package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TrainingApp;

import com.mycompany.myapp.domain.Books;
import com.mycompany.myapp.repository.BooksRepository;
import com.mycompany.myapp.service.BooksService;
import com.mycompany.myapp.service.dto.BooksDTO;
import com.mycompany.myapp.service.mapper.BooksMapper;
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
 * Test class for the BooksResource REST controller.
 *
 * @see BooksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainingApp.class)
public class BooksResourceIntTest {

    private static final String DEFAULT_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PICTURE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_DESCRIBE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIBE = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_1 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_2 = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_2 = "BBBBBBBBBB";

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BooksMapper booksMapper;

    @Autowired
    private BooksService booksService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBooksMockMvc;

    private Books books;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BooksResource booksResource = new BooksResource(booksService);
        this.restBooksMockMvc = MockMvcBuilders.standaloneSetup(booksResource)
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
    public static Books createEntity(EntityManager em) {
        Books books = new Books()
            .picture(DEFAULT_PICTURE)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .number(DEFAULT_NUMBER)
            .count(DEFAULT_COUNT)
            .price(DEFAULT_PRICE)
            .describe(DEFAULT_DESCRIBE)
            .extra1(DEFAULT_EXTRA_1)
            .extra2(DEFAULT_EXTRA_2);
        return books;
    }

    @Before
    public void initTest() {
        books = createEntity(em);
    }

    @Test
    @Transactional
    public void createBooks() throws Exception {
        int databaseSizeBeforeCreate = booksRepository.findAll().size();

        // Create the Books
        BooksDTO booksDTO = booksMapper.toDto(books);
        restBooksMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isCreated());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeCreate + 1);
        Books testBooks = booksList.get(booksList.size() - 1);
        assertThat(testBooks.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testBooks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBooks.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBooks.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBooks.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testBooks.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBooks.getDescribe()).isEqualTo(DEFAULT_DESCRIBE);
        assertThat(testBooks.getExtra1()).isEqualTo(DEFAULT_EXTRA_1);
        assertThat(testBooks.getExtra2()).isEqualTo(DEFAULT_EXTRA_2);
    }

    @Test
    @Transactional
    public void createBooksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = booksRepository.findAll().size();

        // Create the Books with an existing ID
        books.setId(1L);
        BooksDTO booksDTO = booksMapper.toDto(books);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBooksMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);

        // Get all the booksList
        restBooksMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(books.getId().intValue())))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(DEFAULT_PICTURE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].describe").value(hasItem(DEFAULT_DESCRIBE.toString())))
            .andExpect(jsonPath("$.[*].extra1").value(hasItem(DEFAULT_EXTRA_1.toString())))
            .andExpect(jsonPath("$.[*].extra2").value(hasItem(DEFAULT_EXTRA_2.toString())));
    }

    @Test
    @Transactional
    public void getBooks() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);

        // Get the books
        restBooksMockMvc.perform(get("/api/books/{id}", books.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(books.getId().intValue()))
            .andExpect(jsonPath("$.picture").value(DEFAULT_PICTURE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.describe").value(DEFAULT_DESCRIBE.toString()))
            .andExpect(jsonPath("$.extra1").value(DEFAULT_EXTRA_1.toString()))
            .andExpect(jsonPath("$.extra2").value(DEFAULT_EXTRA_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBooks() throws Exception {
        // Get the books
        restBooksMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBooks() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);
        int databaseSizeBeforeUpdate = booksRepository.findAll().size();

        // Update the books
        Books updatedBooks = booksRepository.findOne(books.getId());
        // Disconnect from session so that the updates on updatedBooks are not directly saved in db
        em.detach(updatedBooks);
        updatedBooks
            .picture(UPDATED_PICTURE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .number(UPDATED_NUMBER)
            .count(UPDATED_COUNT)
            .price(UPDATED_PRICE)
            .describe(UPDATED_DESCRIBE)
            .extra1(UPDATED_EXTRA_1)
            .extra2(UPDATED_EXTRA_2);
        BooksDTO booksDTO = booksMapper.toDto(updatedBooks);

        restBooksMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isOk());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
        Books testBooks = booksList.get(booksList.size() - 1);
        assertThat(testBooks.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testBooks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBooks.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBooks.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBooks.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testBooks.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBooks.getDescribe()).isEqualTo(UPDATED_DESCRIBE);
        assertThat(testBooks.getExtra1()).isEqualTo(UPDATED_EXTRA_1);
        assertThat(testBooks.getExtra2()).isEqualTo(UPDATED_EXTRA_2);
    }

    @Test
    @Transactional
    public void updateNonExistingBooks() throws Exception {
        int databaseSizeBeforeUpdate = booksRepository.findAll().size();

        // Create the Books
        BooksDTO booksDTO = booksMapper.toDto(books);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBooksMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isCreated());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBooks() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);
        int databaseSizeBeforeDelete = booksRepository.findAll().size();

        // Get the books
        restBooksMockMvc.perform(delete("/api/books/{id}", books.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Books.class);
        Books books1 = new Books();
        books1.setId(1L);
        Books books2 = new Books();
        books2.setId(books1.getId());
        assertThat(books1).isEqualTo(books2);
        books2.setId(2L);
        assertThat(books1).isNotEqualTo(books2);
        books1.setId(null);
        assertThat(books1).isNotEqualTo(books2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BooksDTO.class);
        BooksDTO booksDTO1 = new BooksDTO();
        booksDTO1.setId(1L);
        BooksDTO booksDTO2 = new BooksDTO();
        assertThat(booksDTO1).isNotEqualTo(booksDTO2);
        booksDTO2.setId(booksDTO1.getId());
        assertThat(booksDTO1).isEqualTo(booksDTO2);
        booksDTO2.setId(2L);
        assertThat(booksDTO1).isNotEqualTo(booksDTO2);
        booksDTO1.setId(null);
        assertThat(booksDTO1).isNotEqualTo(booksDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(booksMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(booksMapper.fromId(null)).isNull();
    }
}
