package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CollectService;
import com.mycompany.myapp.domain.Collect;
import com.mycompany.myapp.repository.CollectRepository;
import com.mycompany.myapp.service.dto.CollectDTO;
import com.mycompany.myapp.service.mapper.CollectMapper;
import com.mycompany.myapp.web.rest.util.ResultObj;
import com.mycompany.myapp.web.rest.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Collect.
 */
@Service
@Transactional
public class CollectServiceImpl implements CollectService {

    private final Logger log = LoggerFactory.getLogger(CollectServiceImpl.class);

    private final CollectRepository collectRepository;

    private final CollectMapper collectMapper;

    public CollectServiceImpl(CollectRepository collectRepository, CollectMapper collectMapper) {
        this.collectRepository = collectRepository;
        this.collectMapper = collectMapper;
    }

    /**
     * Save a collect.
     *
     * @param collectDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CollectDTO save(CollectDTO collectDTO) {
        log.debug("Request to save Collect : {}", collectDTO);
        Collect collect = collectMapper.toEntity(collectDTO);
        collect = collectRepository.save(collect);
        return collectMapper.toDto(collect);
    }

    /**
     * Get all the collects.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CollectDTO> findAll() {
        log.debug("Request to get all Collects");
        return collectRepository.findAll().stream()
            .map(collectMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one collect by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CollectDTO findOne(Long id) {
        log.debug("Request to get Collect : {}", id);
        Collect collect = collectRepository.findOne(id);
        return collectMapper.toDto(collect);
    }

    /**
     * Delete the collect by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Collect : {}", id);
        collectRepository.delete(id);
    }

    @Override
    public ResultObj createCollect(CollectDTO collectDTO) {
        Collect collect = collectMapper.toEntity(collectDTO);
        Collect collect1 = collectRepository.findBySuserIdAndBookId_Id(collect.getSuser().getId(),collect.getBookId().getId());
        if(TypeUtils.isEmpty(collect1)){
            collectRepository.save(collect);
        }
        return ResultObj.backInfo(true,200,"愿望清单已更新",null);
    }

    @Override
    public ResultObj deleteCollect(Long id) {
        collectRepository.delete(id);
        return ResultObj.backInfo(true,200,"删除成功",null);
    }

    @Override
    public List<Collect> selectCollect(Long userId) {
        return collectRepository.findBySuserId(userId);
    }
}
