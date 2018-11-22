package com.mycompany.myapp.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.OrdersService;
import com.mycompany.myapp.service.dto.OrderNewDTO;
import com.mycompany.myapp.service.dto.OrdersDTO;
import com.mycompany.myapp.web.rest.OrdersResource;
import com.mycompany.myapp.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Api(description = "订单表")
@RestController
@RequestMapping("/api")
public class OrderController {

    private final Logger log = LoggerFactory.getLogger(OrdersResource.class);
    private final OrdersService ordersService;

    public OrderController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @ApiOperation("新增订单表 RequestBody")
    @PostMapping("/permit/order")
    @Timed
    public ResultObj createOrder(@RequestBody OrdersDTO ordersDTO) throws URISyntaxException {
        return ordersService.createOrder(ordersDTO);
    }

    @ApiOperation("延迟支付订单表 RequestParam")
    @PostMapping("/permit/order/pay/postpone/id")
    @Timed
    public ResultObj payPostpone(@RequestParam Long id) throws URISyntaxException {
        return ordersService.updateOrderAddress(id);
    }

    @ApiOperation("立即支付 RequestParam")
    @PostMapping("/permit/order/pay")
    @Timed
    public ResultObj payOrder(@RequestParam Long userId,@RequestParam String extra2) throws URISyntaxException {
        return ordersService.payOrder(userId,extra2);
    }

    @ApiOperation("修改立即支付状态 RequestParam")
    @PostMapping("/permit/order/pay/status")
    @Timed
    public ResultObj updatePayOrderStatus(@RequestParam Long userId,@RequestParam String extra2) throws URISyntaxException {
        return ordersService.updatePayOrderStatus(userId,extra2);
    }



    @ApiOperation("查询未完成 RequestParams")
    @GetMapping("/permit/order/select/unfinished/userId")
    @Timed
    public ResultObj selectAllUnfinishedOrders(@RequestParam Long userId) throws URISyntaxException {
        return ResultObj.back(200,ordersService.selectAllUnfinishedOrders(userId));
    }

    @ApiOperation("查询已完成 RequestParams")
    @GetMapping("/permit/order/select/userId")
    @Timed
    public ResultObj selectAllOrders(@RequestParam Long userId) throws URISyntaxException {
        return ResultObj.back(200,ordersService.selectAllOrders(userId));
    }
}
