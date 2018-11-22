package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Orders;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Orders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findBySuserIdAndExtra1(Long userId,String active);

    Orders findBySuserIdAndExtra2(Long id,String extra2);
}
