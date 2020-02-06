package com.wujie.community.controller;

import com.wujie.community.dto.PersonDto;
import com.wujie.community.dto.ResultDto;
import com.wujie.community.enums.ResultCodeEnum;
import com.wujie.community.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private PersonService personService;

    //跳转到登录页面
    @RequestMapping("/toLogin")
    public String toLogin() {

        return "/login.html";
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @PostMapping
    public Object login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password,
                        @RequestParam(name = "code") String code, HttpServletRequest request) {

        //封装参数
        PersonDto personDto = new PersonDto();
        personDto.setUsername(username);
        personDto.setPassword(password);
        personDto.setCode(code);

        //拿到session中的图片验证码
        HttpSession session = request.getSession();
        String imgcode = (String) session.getAttribute("imgcode");
        boolean flag = personService.personLogin(personDto,imgcode);
        if (flag) {
//           return ResultDto.okOf();
            return "redirect:/";
        } else {
           return ResultDto.errorOf(ResultCodeEnum.UNKNOWN_REASON.getCode(),ResultCodeEnum.UNKNOWN_REASON.getMessage());
        }
    }
}
