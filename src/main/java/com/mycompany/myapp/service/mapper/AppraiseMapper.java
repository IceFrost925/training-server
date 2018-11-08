package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AppraiseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Appraise and its DTO AppraiseDTO.
 */
@Mapper(componentModel = "spring", uses = {SUserMapper.class, BooksMapper.class})
public interface AppraiseMapper extends EntityMapper<AppraiseDTO, Appraise> {

    @Mapping(source = "suser.id", target = "suserId")
    @Mapping(source = "bookId.id", target = "bookIdId")
    AppraiseDTO toDto(Appraise appraise);

    @Mapping(source = "suserId", target = "suser")
    @Mapping(source = "bookIdId", target = "bookId")
    Appraise toEntity(AppraiseDTO appraiseDTO);

    default Appraise fromId(Long id) {
        if (id == null) {
            return null;
        }
        Appraise appraise = new Appraise();
        appraise.setId(id);
        return appraise;
    }
}
