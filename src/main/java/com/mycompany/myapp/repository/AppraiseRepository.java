package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Appraise;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Appraise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppraiseRepository extends JpaRepository<Appraise, Long> {

    List<Appraise> findByBookId_Id(Long bookId);
}
