package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Menus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Menus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenusRepository extends JpaRepository<Menus, Long> {
    @Query("select distinct su.type from Menus su")
    List findListMenus();

    @Query("select su.second from Menus su where su.type = ?1")
    List findAllMenusByType(String item);
}
