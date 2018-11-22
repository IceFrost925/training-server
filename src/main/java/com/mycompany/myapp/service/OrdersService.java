package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Orders;
import com.mycompany.myapp.service.dto.OrderListDTO;
import com.mycompany.myapp.service.dto.OrdersDTO;
import com.mycompany.myapp.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing Orders.
 */
public interface OrdersService {

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save
     * @return the persisted entity
     */
    OrdersDTO save(OrdersDTO ordersDTO);

    /**
     * Get all the orders.
     *
     * @return the list of entities
     */
    List<OrdersDTO> findAll();

    /**
     * Get the "id" orders.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OrdersDTO findOne(Long id);

    /**
     * Delete the "id" orders.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ResultObj createOrder(OrdersDTO ordersDTO);

    ResultObj updateOrderAddress(Long id);

    List<OrderListDTO> selectAllOrders(Long userId);

    List<OrderListDTO> selectAllUnfinishedOrders(Long userId);

    ResultObj payOrder(Long id,String extra2);

    ResultObj updatePayOrderStatus(Long userId, String extra2);
}
