package com.wujie.community.controller;

import com.wujie.community.dto.PaginationDto;
import com.wujie.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "currentPage",defaultValue = "1") Integer currentPage,
                        @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize,
                        @RequestParam(name = "search",required = false) String search) {


        //获取quesion完整数据的list(分页查询)
        PaginationDto paginationDto = questionService.list(search,currentPage,pageSize);
        model.addAttribute("pagination", paginationDto);
        model.addAttribute("search",search);
        return "index";
    }
}
