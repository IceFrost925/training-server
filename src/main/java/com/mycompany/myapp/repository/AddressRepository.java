package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.service.dto.AddressDTO;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findBySuserIdAndExtra1OrderById(Long suserId, String active);

    @Query("update Address su set su.extra1 = ?2  where su.id = ?1")
    void deleteAddress(Long id,String unActive);

    Address findBySuserIdAndFlag(Long id, Boolean flag);

    @Query("select su from Address su where su.id = ?1 and su.extra1 = ?2")
    Address findOneById(Long id, String active);

    List<Address> findAddressBySuser_IdAndFlagAndExtra1(Long userId,Boolean flag,String active);
}
