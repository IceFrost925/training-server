package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ShoppingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shopping and its DTO ShoppingDTO.
 */
@Mapper(componentModel = "spring", uses = {SUserMapper.class, BooksMapper.class})
public interface ShoppingMapper extends EntityMapper<ShoppingDTO, Shopping> {

    @Mapping(source = "suser.id", target = "suserId")
    @Mapping(source = "bookId.id", target = "bookIdId")
    ShoppingDTO toDto(Shopping shopping);

    @Mapping(source = "suserId", target = "suser")
    @Mapping(source = "bookIdId", target = "bookId")
    Shopping toEntity(ShoppingDTO shoppingDTO);

    default Shopping fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shopping shopping = new Shopping();
        shopping.setId(id);
        return shopping;
    }
}
