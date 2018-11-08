package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MenusDTO;
import com.mycompany.myapp.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing Menus.
 */
public interface MenusService {

    /**
     * Save a menus.
     *
     * @param menusDTO the entity to save
     * @return the persisted entity
     */
    MenusDTO save(MenusDTO menusDTO);

    /**
     * Get all the menus.
     *
     * @return the list of entities
     */
    List<MenusDTO> findAll();

    /**
     * Get the "id" menus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MenusDTO findOne(Long id);

    /**
     * Delete the "id" menus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    List findListMenus();
}
