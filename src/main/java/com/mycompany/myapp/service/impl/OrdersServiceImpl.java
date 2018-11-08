package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.OrdersService;
import com.mycompany.myapp.domain.Orders;
import com.mycompany.myapp.repository.OrdersRepository;
import com.mycompany.myapp.service.dto.OrdersDTO;
import com.mycompany.myapp.service.mapper.OrdersMapper;
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
 * Service Implementation for managing Orders.
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    private final Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    public OrdersServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
    }

    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrdersDTO save(OrdersDTO ordersDTO) {
        log.debug("Request to save Orders : {}", ordersDTO);
        Orders orders = ordersMapper.toEntity(ordersDTO);
        orders = ordersRepository.save(orders);
        return ordersMapper.toDto(orders);
    }

    /**
     * Get all the orders.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrdersDTO> findAll() {
        log.debug("Request to get all Orders");
        return ordersRepository.findAll().stream()
            .map(ordersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one orders by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OrdersDTO findOne(Long id) {
        log.debug("Request to get Orders : {}", id);
        Orders orders = ordersRepository.findOne(id);
        return ordersMapper.toDto(orders);
    }

    /**
     * Delete the orders by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Orders : {}", id);
        ordersRepository.delete(id);
    }

    @Override
    public ResultObj createOrder(OrdersDTO ordersDTO) {
        if(!TypeUtils.isEmpty(ordersDTO.getBooks())){
            ordersDTO.setExtra1("'0'");
            save(ordersDTO);
            return ResultObj.backInfo(true,200,"生成订单成功",null);
        }
        return ResultObj.backInfo(false,200,"生成订单失败",null);
    }

    @Override
    public ResultObj updateOrderAddress(Long id, Long addressId) {
        OrdersDTO ordersDTO = findOne(id);
        ordersDTO.setAddressIdId(addressId);
        ordersDTO.setExtra1("'1'");
        return null;
    }

    @Override
    public List<Orders> selectAllOrders(Long userId) {
        return ordersRepository.findBySuserIdAndExtra1(userId,"'1'");
    }

    @Override
    public List<Orders> selectAllUnfinishedOrders(Long userId) {
        return ordersRepository.findBySuserIdAndExtra1(userId,"'0'");

    }
}
