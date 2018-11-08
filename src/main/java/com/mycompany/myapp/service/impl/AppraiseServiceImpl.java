package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AppraiseService;
import com.mycompany.myapp.domain.Appraise;
import com.mycompany.myapp.repository.AppraiseRepository;
import com.mycompany.myapp.service.dto.AppraiseDTO;
import com.mycompany.myapp.service.mapper.AppraiseMapper;
import com.mycompany.myapp.web.rest.util.ResultObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Appraise.
 */
@Service
@Transactional
public class AppraiseServiceImpl implements AppraiseService {

    private final Logger log = LoggerFactory.getLogger(AppraiseServiceImpl.class);

    private final AppraiseRepository appraiseRepository;

    private final AppraiseMapper appraiseMapper;

    public AppraiseServiceImpl(AppraiseRepository appraiseRepository, AppraiseMapper appraiseMapper) {
        this.appraiseRepository = appraiseRepository;
        this.appraiseMapper = appraiseMapper;
    }

    /**
     * Save a appraise.
     *
     * @param appraiseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AppraiseDTO save(AppraiseDTO appraiseDTO) {
        log.debug("Request to save Appraise : {}", appraiseDTO);
        Appraise appraise = appraiseMapper.toEntity(appraiseDTO);
        appraise = appraiseRepository.save(appraise);
        return appraiseMapper.toDto(appraise);
    }

    /**
     * Get all the appraises.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AppraiseDTO> findAll() {
        log.debug("Request to get all Appraises");
        return appraiseRepository.findAll().stream()
            .map(appraiseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one appraise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AppraiseDTO findOne(Long id) {
        log.debug("Request to get Appraise : {}", id);
        Appraise appraise = appraiseRepository.findOne(id);
        return appraiseMapper.toDto(appraise);
    }

    /**
     * Delete the appraise by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Appraise : {}", id);
        appraiseRepository.delete(id);
    }

    @Override
    public ResultObj createAppraise(AppraiseDTO appraiseDTO) {
        save(appraiseDTO);
        return ResultObj.backInfo(true,200,"发表成功",null);
    }

    @Override
    public List<Appraise> selectAppraise(Long bookId) {
        return appraiseRepository.findByBookId_Id(bookId);
    }
}
