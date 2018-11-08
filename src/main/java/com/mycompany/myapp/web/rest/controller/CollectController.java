package com.mycompany.myapp.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Collect;
import com.mycompany.myapp.service.CollectService;
import com.mycompany.myapp.service.dto.CollectDTO;
import com.mycompany.myapp.web.rest.CollectResource;
import com.mycompany.myapp.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Api(description = "愿望清单")
@RestController
@RequestMapping("/api")
public class CollectController {

    private final Logger log = LoggerFactory.getLogger(CollectResource.class);
    private final CollectService collectService;

    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @ApiOperation("新增愿望清单 RequestBody")
    @PostMapping("/permit/collect")
    @Timed
    public ResultObj createCollect(@RequestBody CollectDTO collect) throws URISyntaxException {
        return collectService.createCollect(collect);
    }

    @ApiOperation("查询愿望清单 RequestParams")
    @PostMapping("/permit/collect/select/userId")
    @Timed
    public ResultObj selectCollect(@RequestParam Long userId) throws URISyntaxException {
        return ResultObj.back(20,collectService.selectCollect(userId));
    }


    @ApiOperation("删除愿望清单 RequestParams")
    @DeleteMapping("/permit/collect/delete")
    @Timed
    public ResultObj deleteCollect(@RequestParam Long id) throws URISyntaxException {
        return collectService.deleteCollect(id);
    }
}
