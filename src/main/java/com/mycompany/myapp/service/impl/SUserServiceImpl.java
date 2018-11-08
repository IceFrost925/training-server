package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SUserService;
import com.mycompany.myapp.domain.SUser;
import com.mycompany.myapp.repository.SUserRepository;
import com.mycompany.myapp.service.dto.SUserDTO;
import com.mycompany.myapp.service.mapper.SUserMapper;
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
 * Service Implementation for managing SUser.
 */
@Service
@Transactional
public class SUserServiceImpl implements SUserService {

    private final Logger log = LoggerFactory.getLogger(SUserServiceImpl.class);

    private final SUserRepository sUserRepository;

    private final SUserMapper sUserMapper;

    public SUserServiceImpl(SUserRepository sUserRepository, SUserMapper sUserMapper) {
        this.sUserRepository = sUserRepository;
        this.sUserMapper = sUserMapper;
    }

    /**
     * Save a sUser.
     *
     * @param sUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SUserDTO save(SUserDTO sUserDTO) {
        log.debug("Request to save SUser : {}", sUserDTO);
        SUser sUser = sUserMapper.toEntity(sUserDTO);
        sUser = sUserRepository.save(sUser);
        return sUserMapper.toDto(sUser);
    }

    /**
     * Get all the sUsers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SUserDTO> findAll() {
        log.debug("Request to get all SUsers");
        return sUserRepository.findAll().stream()
            .map(sUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one sUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SUserDTO findOne(Long id) {
        log.debug("Request to get SUser : {}", id);
        SUser sUser = sUserRepository.findOne(id);
        return sUserMapper.toDto(sUser);
    }

    /**
     * Delete the sUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SUser : {}", id);
        sUserRepository.delete(id);
    }

    @Override
    public ResultObj insertsUser(SUser sUser) {
        if(!TypeUtils.isEmpty(sUser)){
            if(TypeUtils.isEmpty(sUser.getId())){
                sUser.setExtra1("'1'");
                sUserRepository.save(sUser);
                return ResultObj.backInfo(true,200,"新增成功",null);
            }
        }
        return ResultObj.backInfo(false,200,"新增失败",null);
    }

    @Override
    public SUser findSUser(String username,String password) {
        return sUserRepository.findUser(username,password,"'1'");
    }

    @Override
    public ResultObj updateSUser(SUser sUser) {
        if(!TypeUtils.isEmpty(sUser)){
            if(!TypeUtils.isEmpty(sUser.getId())){
                sUserRepository.save(sUser);
                return ResultObj.backInfo(true,200,"新增成功",null);
            }
        }
        return ResultObj.backInfo(false,200,"新增失败",null);
    }

    @Override
    public SUser findSUserByUsername(String username) {
        return sUserRepository.findSUserByUsername(username,"'1'");
    }
}
