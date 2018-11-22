package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Books;
import com.mycompany.myapp.domain.Shopping;
import com.mycompany.myapp.repository.BooksRepository;
import com.mycompany.myapp.repository.ShoppingRepository;
import com.mycompany.myapp.service.OrdersService;
import com.mycompany.myapp.domain.Orders;
import com.mycompany.myapp.repository.OrdersRepository;
import com.mycompany.myapp.service.dto.OrderListDTO;
import com.mycompany.myapp.service.dto.OrdersDTO;
import com.mycompany.myapp.service.mapper.OrdersMapper;
import com.mycompany.myapp.web.rest.util.ResultObj;
import com.mycompany.myapp.web.rest.util.TypeUtils;
import net.minidev.json.JSONObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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

    private final ShoppingRepository shoppingRepository;

    public OrdersServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper,ShoppingRepository shoppingRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.shoppingRepository = shoppingRepository;
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

    /*
    * 未完成到完成
    * */
    @Override
    public ResultObj updateOrderAddress(Long id) {
        OrdersDTO ordersDTO = findOne(id);
        ordersDTO.setExtra1("'1'");
        save(ordersDTO);
        return ResultObj.backInfo(true, 200, "支付成功", null);
    }

    @Override
    public List<OrderListDTO> selectAllOrders(Long userId) {
        List<OrderListDTO> listDTOS = new ArrayList<>();
        List<Orders> ordersList = ordersRepository.findBySuserIdAndExtra1(userId,"'1'");
        for(Orders orders : ordersList){
            OrderListDTO orderListDTO = new OrderListDTO();
            orderListDTO.setId(orders.getId());
            String srt = orders.getBooks().substring(1,orders.getBooks().length()-1);
            String[] strings = srt.split(",");
            List<Shopping> shoppingList = new ArrayList<>();
            for(int i = 0;i<strings.length;i++){
                Shopping shopping = shoppingRepository.findOne(Long.valueOf(strings[i]));
                shoppingList.add(shopping);
            }
            orderListDTO.setShoppings(shoppingList);
            orderListDTO.setAddress(orders.getAddressId());
            orderListDTO.setsUser(orders.getSuser());
            log.info(orderListDTO.toString());
            listDTOS.add(orderListDTO);
        }
        return listDTOS;
    }

    @Override
    public List<OrderListDTO> selectAllUnfinishedOrders(Long userId) {
        List<OrderListDTO> listDTOS = new ArrayList<>();
        List<Orders> ordersList = ordersRepository.findBySuserIdAndExtra1(userId,"'0'");
        for(Orders orders : ordersList){
            OrderListDTO orderListDTO = new OrderListDTO();
            orderListDTO.setId(orders.getId());
            String srt = orders.getBooks().substring(1,orders.getBooks().length()-1);
            String[] strings = srt.split(",");
            List<Shopping> shoppingList = new ArrayList<>();
            for(int i = 0;i<strings.length;i++){
                Shopping shopping = shoppingRepository.findOne(Long.valueOf(strings[i]));
                shoppingList.add(shopping);
            }
            orderListDTO.setShoppings(shoppingList);
            orderListDTO.setAddress(orders.getAddressId());
            orderListDTO.setsUser(orders.getSuser());
            log.info(orderListDTO.toString());
            listDTOS.add(orderListDTO);
        }
        return listDTOS;
    }

    @Override
    public ResultObj payOrder(Long id,String extra2) {
        Orders orders = ordersRepository.findBySuserIdAndExtra2(id,extra2);
        orders.setExtra1("'1'");
        orders.setExtra2("");
        orders = ordersRepository.save(orders);
        ordersMapper.toDto(orders);
        return ResultObj.backInfo(true,200,"支付成功",null);
    }

    @Override
    public ResultObj updatePayOrderStatus(Long userId, String extra2) {
        Orders orders = ordersRepository.findBySuserIdAndExtra2(userId,extra2);
        orders.setExtra2("");
        orders = ordersRepository.save(orders);
        ordersMapper.toDto(orders);
        return ResultObj.backInfo(true,200,"OK",null);
    }
}
