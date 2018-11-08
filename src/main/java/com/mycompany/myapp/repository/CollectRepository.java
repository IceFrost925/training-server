package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Collect;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Collect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectRepository extends JpaRepository<Collect, Long> {

    Collect findBySuserIdAndBookId_Id(Long userId,Long bookId);

    List<Collect> findBySuserId(Long userId);
}
