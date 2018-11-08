package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.BooksService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.BooksDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Books.
 */
@RestController
@RequestMapping("/api")
public class BooksResource {

    private final Logger log = LoggerFactory.getLogger(BooksResource.class);

    private static final String ENTITY_NAME = "books";

    private final BooksService booksService;

    public BooksResource(BooksService booksService) {
        this.booksService = booksService;
    }

    /**
     * POST  /books : Create a new books.
     *
     * @param booksDTO the booksDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new booksDTO, or with status 400 (Bad Request) if the books has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/books")
    @Timed
    public ResponseEntity<BooksDTO> createBooks(@RequestBody BooksDTO booksDTO) throws URISyntaxException {
        log.debug("REST request to save Books : {}", booksDTO);
        if (booksDTO.getId() != null) {
            throw new BadRequestAlertException("A new books cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BooksDTO result = booksService.save(booksDTO);
        return ResponseEntity.created(new URI("/api/books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /books : Updates an existing books.
     *
     * @param booksDTO the booksDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated booksDTO,
     * or with status 400 (Bad Request) if the booksDTO is not valid,
     * or with status 500 (Internal Server Error) if the booksDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/books")
    @Timed
    public ResponseEntity<BooksDTO> updateBooks(@RequestBody BooksDTO booksDTO) throws URISyntaxException {
        log.debug("REST request to update Books : {}", booksDTO);
        if (booksDTO.getId() == null) {
            return createBooks(booksDTO);
        }
        BooksDTO result = booksService.save(booksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, booksDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /books : get all the books.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of books in body
     */
    @GetMapping("/books")
    @Timed
    public List<BooksDTO> getAllBooks() {
        log.debug("REST request to get all Books");
        return booksService.findAll();
        }

    /**
     * GET  /books/:id : get the "id" books.
     *
     * @param id the id of the booksDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the booksDTO, or with status 404 (Not Found)
     */
    @GetMapping("/books/{id}")
    @Timed
    public ResponseEntity<BooksDTO> getBooks(@PathVariable Long id) {
        log.debug("REST request to get Books : {}", id);
        BooksDTO booksDTO = booksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(booksDTO));
    }

    /**
     * DELETE  /books/:id : delete the "id" books.
     *
     * @param id the id of the booksDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/books/{id}")
    @Timed
    public ResponseEntity<Void> deleteBooks(@PathVariable Long id) {
        log.debug("REST request to delete Books : {}", id);
        booksService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
