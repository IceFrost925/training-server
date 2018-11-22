package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Books;
import com.mycompany.myapp.service.dto.BooksDTO;
import com.mycompany.myapp.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing Books.
 */
public interface BooksService {

    /**
     * Save a books.
     *
     * @param booksDTO the entity to save
     * @return the persisted entity
     */
    BooksDTO save(BooksDTO booksDTO);

    /**
     * Get all the books.
     *
     * @return the list of entities
     */
    List<BooksDTO> findAll();

    /**
     * Get the "id" books.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BooksDTO findOne(Long id);

    /**
     * Delete the "id" books.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ResultObj insertsBooks(Books books);

    ResultObj updateBooks(Long id, String count);

    List<Books> selectBooksByType(String type);

    Books selectBooksById(Long id);

    List<Books> selectBooksByStar(String star);

    List<Books> selectBooksLasted();

    List<Books> selectBooksByFirstType(String type);

    List<Books> selectBooksByName(String name);
}
