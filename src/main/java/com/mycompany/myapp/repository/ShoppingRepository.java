package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Shopping;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Shopping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoppingRepository extends JpaRepository<Shopping, Long> {

    List<Shopping> findBySuserIdAndExtra1(Long id,String active);

    Shopping findByBookId_IdAndSuser_Id(Long bookId,Long userId);
}
