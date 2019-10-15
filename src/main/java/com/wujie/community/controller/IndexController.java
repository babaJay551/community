package com.wujie.community.controller;

import com.wujie.community.dto.PaginationDto;
import com.wujie.community.dto.QuesionDto;
import com.wujie.community.mapper.UserMapper;
import com.wujie.community.model.User;
import com.wujie.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request, Model model,
                        @RequestParam(name = "currentPage",defaultValue = "1") Integer currentPage,
                        @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize) {

        //获取cookie
        Cookie[] cookies = request.getCookies();
        //判断cookie中是否存在对象 (判断对象是否登录过)
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                //如果cookie中有值 证明用户登录过
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //通过githubUser获取唯一表示 token 查找对象
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        //放入session域中  方便前端页面显示
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }

        //获取quesion完整数据的list(分页查询)
        PaginationDto paginationDto = questionService.list(currentPage,pageSize);
        model.addAttribute("pagination", paginationDto);

        return "index";
    }
}
