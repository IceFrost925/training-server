package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orders and its DTO OrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {SUserMapper.class, ShoppingMapper.class})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {

    @Mapping(source = "suser.id", target = "suserId")
    @Mapping(source = "shoppingId.id", target = "shoppingIdId")
    OrdersDTO toDto(Orders orders);

    @Mapping(source = "suserId", target = "suser")
    @Mapping(source = "shoppingIdId", target = "shoppingId")
    Orders toEntity(OrdersDTO ordersDTO);

    default Orders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orders orders = new Orders();
        orders.setId(id);
        return orders;
    }
}
