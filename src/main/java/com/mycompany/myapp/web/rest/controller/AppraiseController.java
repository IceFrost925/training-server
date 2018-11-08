package com.mycompany.myapp.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Appraise;
import com.mycompany.myapp.domain.Collect;
import com.mycompany.myapp.service.AppraiseService;
import com.mycompany.myapp.service.dto.AppraiseDTO;
import com.mycompany.myapp.web.rest.AppraiseResource;
import com.mycompany.myapp.web.rest.util.ResultObj;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Api(description = "书籍评价")
@RestController
@RequestMapping("/api")
public class AppraiseController {
    private final Logger log = LoggerFactory.getLogger(AppraiseResource.class);
    private final AppraiseService appraiseService;

    public AppraiseController(AppraiseService appraiseService) {
        this.appraiseService = appraiseService;
    }

    @ApiOperation("添加评论 RequestBody")
    @PostMapping("/permit/apparaise")
    @Timed
    public ResultObj createAppraise(@RequestBody AppraiseDTO appraise) throws URISyntaxException {
        return appraiseService.createAppraise(appraise);
    }


    @ApiOperation("查询评论 RequestParams")
    @PostMapping("/permit/apparaise/select/bookId")
    @Timed
    public ResultObj selectAppraise(@RequestParam Long bookId) throws URISyntaxException {
        return ResultObj.back(200,appraiseService.selectAppraise(bookId));
    }
}
