package com.mycompany.myapp.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Shopping;
import com.mycompany.myapp.service.ShoppingService;
import com.mycompany.myapp.service.dto.ShoppingDTO;
import com.mycompany.myapp.web.rest.ShoppingResource;
import com.mycompany.myapp.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@Api(description = "购物车管理")
@RestController
@RequestMapping("/api")
public class ShoppingController {
    private final Logger log = LoggerFactory.getLogger(ShoppingResource.class);
    private final ShoppingService shoppingService;
    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @ApiOperation("添加购物车 RequestBody")
    @PostMapping("/permit/shopping")
    @Timed
    public ResultObj createShopping(@RequestBody ShoppingDTO shopping) throws URISyntaxException {
        return shoppingService.createShopping(shopping);
    }

    @ApiOperation("查询全部ByUserId RequestParams")
    @PostMapping("/permit/shopping/select/userId")
    @Timed
    public ResultObj selectShopping(@ApiParam(name = "userId",value = "userId") @RequestParam Long userId) throws URISyntaxException {
        return ResultObj.back(200,shoppingService.selectShopping(userId));
    }

    @ApiOperation("修改购买数量 RequestParams")
    @PostMapping("/permit/shopping/update/number")
    @Timed
    public ResultObj updateShopping(@ApiParam(name = "id",value = "id") @RequestParam Long id,
                                    @ApiParam(name = "number",value = "数量") @RequestParam String number) throws URISyntaxException {
        return shoppingService.updateShoppingNumber(id,number);
    }

    @ApiOperation("查询shoppingListById RequestParams")
    @PostMapping("/permit/shopping/idList")
    @Timed
    public ResultObj getShoppingList(@ApiParam(name = "idList",value = "idList") @RequestParam String[] idList) throws URISyntaxException {
        return ResultObj.back(200,shoppingService.selectShoppingByIdList(idList));
    }

    @ApiOperation("删除某个商品 RequestParams")
    @DeleteMapping("/permit/shopping/delete/idList")
    @Timed
    public ResultObj deleteShoppingList(@ApiParam(name = "idList",value = "idList") @RequestParam String[] idList) throws URISyntaxException {
        return shoppingService.deleteShoppingList(idList);
    }


    @ApiOperation("删除某个商品 RequestParams")
    @DeleteMapping("/permit/shopping/delete/id")
    @Timed
    public ResultObj deleteShopping(@ApiParam(name = "id",value = "id") @RequestParam Long id) throws URISyntaxException {
        return shoppingService.deleteShopping(id);
    }




}
