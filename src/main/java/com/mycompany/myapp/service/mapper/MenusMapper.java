package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MenusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Menus and its DTO MenusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MenusMapper extends EntityMapper<MenusDTO, Menus> {



    default Menus fromId(Long id) {
        if (id == null) {
            return null;
        }
        Menus menus = new Menus();
        menus.setId(id);
        return menus;
    }
}
