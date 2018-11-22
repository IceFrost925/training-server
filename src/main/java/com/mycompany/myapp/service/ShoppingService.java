package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Shopping;
import com.mycompany.myapp.service.dto.ShoppingDTO;
import com.mycompany.myapp.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing Shopping.
 */
public interface ShoppingService {

    /**
     * Save a shopping.
     *
     * @param shoppingDTO the entity to save
     * @return the persisted entity
     */
    ShoppingDTO save(ShoppingDTO shoppingDTO);

    /**
     * Get all the shoppings.
     *
     * @return the list of entities
     */
    List<ShoppingDTO> findAll();

    /**
     * Get the "id" shopping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShoppingDTO findOne(Long id);

    /**
     * Delete the "id" shopping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ResultObj createShopping(ShoppingDTO shopping);

    ResultObj updateShoppingNumber(Long bookId, String number);

    ResultObj deleteShopping(Long bookId);

    List<Shopping> selectShopping(Long userId);

    List<Shopping> selectShoppingByIdList(String[] idList);

    ResultObj deleteShoppingList(String[] idList);
}
