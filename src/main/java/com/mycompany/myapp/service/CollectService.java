package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Collect;
import com.mycompany.myapp.service.dto.CollectDTO;
import com.mycompany.myapp.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing Collect.
 */
public interface CollectService {

    /**
     * Save a collect.
     *
     * @param collectDTO the entity to save
     * @return the persisted entity
     */
    CollectDTO save(CollectDTO collectDTO);

    /**
     * Get all the collects.
     *
     * @return the list of entities
     */
    List<CollectDTO> findAll();

    /**
     * Get the "id" collect.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CollectDTO findOne(Long id);

    /**
     * Delete the "id" collect.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ResultObj createCollect(CollectDTO collect);

    ResultObj deleteCollect(Long id);

    List<Collect> selectCollect(Long userId);
}
