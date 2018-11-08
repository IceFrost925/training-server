package com.mycompany.myapp.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SUser;
import com.mycompany.myapp.service.SUserService;
import com.mycompany.myapp.web.rest.SUserResource;
import com.mycompany.myapp.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
@Api(description = "普通用户管理")
@RestController
@RequestMapping("/api")
public class SUserController {

    private final Logger log = LoggerFactory.getLogger(com.mycompany.myapp.web.rest.SUserResource.class);
    private final SUserService sUserService;

    public SUserController(SUserService sUserService) {
        this.sUserService = sUserService;
    }

    @ApiOperation("新增用户 RequestBody")
    @PostMapping("/permit/suser/insert")
    @Timed
    public ResultObj createSUser(@ApiParam(name="sUser",value="用户实体",required=true) @RequestBody SUser sUser) throws URISyntaxException {
        return sUserService.insertsUser(sUser);
    }

    @ApiOperation("用户登陆 RequestParam")
    @PostMapping("/permit/suser/login")
    @Timed
    public ResultObj findSUser(@ApiParam(name="username",value="用户名或者邮箱",required=true) @RequestParam String username,
                                    @ApiParam(name="password",value="新闻标题",required=true) @RequestParam String password) {
        return ResultObj.back(200,sUserService.findSUser(username, password));
    }

    @ApiOperation("查询用户信息 RequestParam")
    @PostMapping("/permit/suser/select")
    @Timed
    public ResultObj findSUser(@ApiParam(name="username",value="仅用户名",required=true) @RequestParam String username) {
        return ResultObj.back(200,sUserService.findSUserByUsername(username));
    }

    @ApiOperation("修改用户 RequestBody")
    @PostMapping("/permit/suser/update")
    @Timed
    public ResultObj updateUserInfo(@ApiParam(name = "sUser", value = "用户实体", required = true) @RequestBody SUser sUser) throws URISyntaxException{
        return sUserService.updateSUser(sUser);
    }

}
