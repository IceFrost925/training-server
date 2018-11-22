package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Books;
import com.mycompany.myapp.domain.Menus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Books entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BooksRepository extends JpaRepository<Books, Long>{
    @Query("select su from Books su where (su.type like concat('%',?1,'%') or su.extra2 like concat('%',?1,'%'))")
    List<Books> selectBooksByType(String type);

    @Query("select su from Books su where su.id = ?1")
    Books selectBooksById(Long id);

    @Query("select su from Books su where su.extra1 >= ?1")
    List<Books> selectBooksByStar(String star);

    @Query("select su from Books su order by su.id desc")
    List<Books> selectBooksLasted();


    @Query("select su from Books su order by su.id desc")
    List<Books> selectBooksByFirstType(String type);

    @Query("select su from Books su where su.name = ?1")
    Books  findBooksByOnlyName(String name);

    @Query("select su from Books su where su.name like concat('%',?1,'%')")
    List<Books>  findBooksByName(String name);
}
