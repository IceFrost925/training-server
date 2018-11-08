package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SUser;
import com.mycompany.myapp.service.dto.SUserDTO;
import com.mycompany.myapp.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing SUser.
 */
public interface SUserService {

    /**
     * Save a sUser.
     *
     * @param sUserDTO the entity to save
     * @return the persisted entity
     */
    SUserDTO save(SUserDTO sUserDTO);

    /**
     * Get all the sUsers.
     *
     * @return the list of entities
     */
    List<SUserDTO> findAll();

    /**
     * Get the "id" sUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SUserDTO findOne(Long id);

    /**
     * Delete the "id" sUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ResultObj insertsUser(SUser sUser);

    SUser findSUser(String username, String password);

    ResultObj updateSUser(SUser sUser);

    SUser findSUserByUsername(String username);
}
