package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.MenusService;
import com.mycompany.myapp.domain.Menus;
import com.mycompany.myapp.repository.MenusRepository;
import com.mycompany.myapp.service.dto.MenusDTO;
import com.mycompany.myapp.service.dto.MenusListDTO;
import com.mycompany.myapp.service.mapper.MenusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Menus.
 */
@Service
@Transactional
public class MenusServiceImpl implements MenusService {

    private final Logger log = LoggerFactory.getLogger(MenusServiceImpl.class);

    private final MenusRepository menusRepository;

    private final MenusMapper menusMapper;

    public MenusServiceImpl(MenusRepository menusRepository, MenusMapper menusMapper) {
        this.menusRepository = menusRepository;
        this.menusMapper = menusMapper;
    }



    /**
     * Save a menus.
     *
     * @param menusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MenusDTO save(MenusDTO menusDTO) {
        log.debug("Request to save Menus : {}", menusDTO);
        Menus menus = menusMapper.toEntity(menusDTO);
        menus = menusRepository.save(menus);
        return menusMapper.toDto(menus);
    }

    /**
     * Get all the menus.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MenusDTO> findAll() {
        log.debug("Request to get all Menus");
        return menusRepository.findAll().stream()
            .map(menusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one menus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MenusDTO findOne(Long id) {
        log.debug("Request to get Menus : {}", id);
        Menus menus = menusRepository.findOne(id);
        return menusMapper.toDto(menus);
    }

    /**
     * Delete the menus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Menus : {}", id);
        menusRepository.delete(id);
    }

    @Override
    public List findListMenus() {
        List menusList = menusRepository.findListMenus();
        List<MenusListDTO> menusListDTOList = new ArrayList<>();
        menusList.forEach((item) ->{
            MenusListDTO menusListDTO = new MenusListDTO();
            List second = menusRepository.findAllMenusByType(String.valueOf(item));
            menusListDTO.setFirst(String.valueOf(item));
            menusListDTO.setSecond(second);
            menusListDTOList.add(menusListDTO);
        });
        return menusListDTOList;
    }
}
