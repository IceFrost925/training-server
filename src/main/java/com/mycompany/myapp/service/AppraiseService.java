package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Appraise;
import com.mycompany.myapp.service.dto.AppraiseDTO;
import com.mycompany.myapp.web.rest.util.ResultObj;

import java.util.List;

/**
 * Service Interface for managing Appraise.
 */
public interface AppraiseService {

    /**
     * Save a appraise.
     *
     * @param appraiseDTO the entity to save
     * @return the persisted entity
     */
    AppraiseDTO save(AppraiseDTO appraiseDTO);

    /**
     * Get all the appraises.
     *
     * @return the list of entities
     */
    List<AppraiseDTO> findAll();

    /**
     * Get the "id" appraise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AppraiseDTO findOne(Long id);

    /**
     * Delete the "id" appraise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    ResultObj createAppraise(AppraiseDTO appraise);

    List<Appraise> selectAppraise(Long bookId);
}
