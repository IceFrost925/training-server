package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SUser and its DTO SUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SUserMapper extends EntityMapper<SUserDTO, SUser> {



    default SUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        SUser sUser = new SUser();
        sUser.setId(id);
        return sUser;
    }
}
