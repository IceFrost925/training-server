package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CollectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Collect and its DTO CollectDTO.
 */
@Mapper(componentModel = "spring", uses = {SUserMapper.class, BooksMapper.class})
public interface CollectMapper extends EntityMapper<CollectDTO, Collect> {

    @Mapping(source = "suser.id", target = "suserId")
    @Mapping(source = "bookId.id", target = "bookIdId")
    CollectDTO toDto(Collect collect);

    @Mapping(source = "suserId", target = "suser")
    @Mapping(source = "bookIdId", target = "bookId")
    Collect toEntity(CollectDTO collectDTO);

    default Collect fromId(Long id) {
        if (id == null) {
            return null;
        }
        Collect collect = new Collect();
        collect.setId(id);
        return collect;
    }
}
