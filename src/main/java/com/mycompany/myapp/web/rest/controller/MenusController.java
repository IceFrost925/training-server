package com.mycompany.myapp.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.MenusService;
import com.mycompany.myapp.web.rest.MenusResource;
import com.mycompany.myapp.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(description = "导航条")
@RestController
@RequestMapping("/api")
public class MenusController {
    private final Logger log = LoggerFactory.getLogger(MenusResource.class);
    private final MenusService menusService;
    public MenusController(MenusService menusService) {
        this.menusService = menusService;
    }

    @ApiOperation("查询所有 RequestParam")
    @GetMapping("/permit/menus/select/all")
    @Timed
    public ResultObj findAllMenus() {
        return ResultObj.back(200,menusService.findListMenus());
    }
}
