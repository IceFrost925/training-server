package com.mycompany.myapp.web.rest.controller;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Books;
import com.mycompany.myapp.domain.SUser;
import com.mycompany.myapp.service.BooksService;
import com.mycompany.myapp.web.rest.BooksResource;
import com.mycompany.myapp.web.rest.util.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
@Api(description = "书籍管理")
@RestController
@RequestMapping("/api")
public class BooksController {
    private final Logger log = LoggerFactory.getLogger(BooksResource.class);
    private final BooksService booksService;
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @ApiOperation("新增书籍 RequestBody")
    @PostMapping("/permit/books/insert")
    @Timed
    public ResultObj insertsBooks(@ApiParam(name="books",value="用户实体",required=true) @RequestBody Books books) throws URISyntaxException {
        return booksService.insertsBooks(books);
    }

    @ApiOperation("修改书籍 RequestParam")
    @PostMapping("/permit/books/update")
    @Timed
    public ResultObj updateBooks(@ApiParam(name="id",value="bookId",required=true) @RequestParam Long id,
                               @ApiParam(name="count",value="数量",required=true) @RequestParam String count) {
        return booksService.updateBooks(id, count);
    }

    @ApiOperation("根据类型type查 RequestParam")
    @PostMapping("/permit/books/select/type")
    @Timed
    public ResultObj selectBooksByType(@ApiParam(name="type",value="类型",required=true) @RequestParam String type) {
        return ResultObj.back(200,booksService.selectBooksByType(type));
    }

    @ApiOperation("根据Id查 RequestParam")
    @PostMapping("/permit/books/select/id")
    @Timed
    public ResultObj selectBooksById(@ApiParam(name="id",value="ID",required=true) @RequestParam Long id) {
        return ResultObj.back(200,booksService.selectBooksById(id));
    }

    @ApiOperation("根据extra1(星级)查 RequestParam")
    @PostMapping("/permit/books/select/extra1")
    @Timed
    public ResultObj selectBooksByStar(@ApiParam(name="extra1",value="星级",required=true) @RequestParam String extra1) {
        return ResultObj.back(200,booksService.selectBooksByStar(extra1));
    }

    @ApiOperation("查最新书籍 RequestParam")
    @GetMapping("/permit/books/select/lasted")
    @Timed
    public ResultObj selectBooksLasted() {
        return ResultObj.back(200,booksService.selectBooksLasted());
    }


    @ApiOperation("根据根类型查书籍 RequestParam")
    @PostMapping("/permit/books/select/first/type")
    @Timed
    public ResultObj selectBooksByFirstType(@ApiParam(name="type",value="类型",required=true) @RequestParam String type) {
        return ResultObj.back(200,booksService.selectBooksByFirstType(type));
    }

    @ApiOperation("根据书籍名字查 RequestParam")
    @PostMapping("/permit/books/select/name")
    @Timed
    public ResultObj selectBooksByName(@ApiParam(name="name",value="书名",required=true) @RequestParam String name) {
        return ResultObj.back(200,booksService.selectBooksByName(name));
    }



}
