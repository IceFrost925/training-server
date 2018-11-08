package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Books;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Books entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    @Query("select su from Books su where (su.type like concat('%',?1,'%') or su.extra1 like concat('%',?1,'%'))")
    List<Books> selectBooksByType(String type);

    @Query("select su from Books su where su.id = ?1")
    Books selectBooksById(Long id);
}
