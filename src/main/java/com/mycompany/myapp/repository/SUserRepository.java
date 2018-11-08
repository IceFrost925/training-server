package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SUser;
import com.mycompany.myapp.web.rest.util.ResultObj;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SUserRepository extends JpaRepository<SUser, Long> {
    @Query("select su from SUser su where (su.username like concat('%',?1,'%') or su.email like concat('%',?1,'%') )" +
        "and su.passwd = ?2" +
        " and su.extra1 = ?3")
    SUser findUser(String username, String password,String active);

    @Query("select su from SUser su where su.username = ?1 and su.extra1 = ?2")
    SUser findSUserByUsername(String username,String active);
}
