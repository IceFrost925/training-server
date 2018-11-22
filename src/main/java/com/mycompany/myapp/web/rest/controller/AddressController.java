package com.mycompany.myapp.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.AddressService;
import com.mycompany.myapp.service.dto.AddressDTO;
import com.mycompany.myapp.web.rest.AddressResource;
import com.mycompany.myapp.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.net.URISyntaxException;

@Api(description = "收货地址管理")
@RestController
@RequestMapping("/api")
public class AddressController {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @ApiOperation("新增地址 RequestBody")
    @PostMapping("/permit/addresses")
    @Timed
    public ResultObj createAddress(@RequestBody AddressDTO addressDTO) throws URISyntaxException {
       return addressService.createAddress(addressDTO);
    }

    @ApiOperation("修改地址 RequestBody")
    @PutMapping("/permit/addresses/update")
    @Timed
    public ResultObj updateAddress(@RequestBody AddressDTO addressDTO) throws URISyntaxException {
        return addressService.updateAddress(addressDTO);
    }

    @ApiOperation("修改默认地址 RequestBody")
    @PostMapping("/permit/addresses/update/default")
    @Timed
    public ResultObj updateDefaultAddress(@ApiParam(name="userId",value="addressId",required=true) @RequestParam Long userId,
                                          @ApiParam(name="AddressId",value="addressId",required=true) @RequestParam Long AddressId) throws URISyntaxException {
        return addressService.updateDefaultAddress(userId,AddressId);
    }

    @ApiOperation("查询全部地址 RequestBody")
    @GetMapping("/permit/addresses/all/userId")
    @Timed
    public ResultObj getAllAddresses(@RequestParam Long userId) {
        return ResultObj.back(200,addressService.findAllAddress(userId));
    }

    @ApiOperation("查询地址(是否默认) RequestParam")
    @PostMapping("/permit/addresses/default")
    @Timed
    public ResultObj getDefaultAddresses(@RequestParam Long userId,Boolean flag) {
        return ResultObj.back(200,addressService.getDefaultAddresses(userId,flag));
    }


    @ApiOperation("根据id查询 RequestParams")
    @GetMapping("/permit/addresses/select/id")
    @Timed
    public ResultObj getAddress(@ApiParam(name="id",value="addressId",required=true) @RequestParam Long id)throws URISyntaxException{
        return ResultObj.back(200, addressService.findOneById(id));
    }

    @ApiOperation("根据id删除 RequestParams")
    @DeleteMapping("/permit/addresses/delete")
    @Timed
    public ResultObj deleteAddress(@ApiParam(name="userId",value="userId",required=true) @RequestParam Long userId,
                                    @ApiParam(name="addressId",value="addressId",required=true) @RequestParam Long addressId)throws URISyntaxException {
        return addressService.deleteAddress(userId,addressId);
    }
}
