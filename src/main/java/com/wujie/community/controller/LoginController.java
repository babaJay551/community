package com.wujie.community.controller;

import com.wujie.community.dto.ResultDto;
import com.wujie.community.dto.UserDto;
import com.wujie.community.enums.ResultCodeEnum;
import com.wujie.community.model.User;
import com.wujie.community.service.UserService;
import com.wujie.community.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    //跳转到登录页面
    @RequestMapping("/toLogin")
    public String toLogin() {

        return "/login.html";
    }

    /**
     * 登录
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultDto login(@RequestBody UserDto personDto,
                           HttpServletRequest request) {

        //对用户登录传过来的哈希密码先进行加密
        String s=personDto.getPassword();
        String smi= MD5Util.MD5(s);
        //加密后，与数据库存储的密码进行比对
        personDto.setPassword(smi);
        //拿到session中的图片验证码
        HttpSession session = request.getSession();
        String imgcode = (String) session.getAttribute("imgcode");
        User user = userService.userLogin(personDto,imgcode);
        if (user.getId()!=null&&user!=null) {
            //登录成功显示我的信息 并跳转到首页
            request.getSession().setAttribute("user",user);
            //登录成功
            return ResultDto.okOf();
        } else {
           return ResultDto.errorOf(ResultCodeEnum.UNKNOWN_REASON.getCode(),ResultCodeEnum.UNKNOWN_REASON.getMessage());
        }
    }

    /**
     * 退出登录
     * @param request
     * @return
     */

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        //删除session
        request.getSession().invalidate();
        return "redirect:/";
    }
}
