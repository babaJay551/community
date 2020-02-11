package com.wujie.community.controller;

import com.wujie.community.dto.AccessTokenDto;
import com.wujie.community.dto.GithubUser;
import com.wujie.community.model.User;
import com.wujie.community.provider.GithubProvider;
import com.wujie.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 第三方登录功能（弃用）
 */

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

//    @Value("${github.client.id}")
    private String clientid;
//    @Value("${github.client.secret}")
    private String clientSecret;
//    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response){

        AccessTokenDto tokenDto = new AccessTokenDto();
        tokenDto.setClient_id(clientid);
        tokenDto.setClient_secret(clientSecret);
        tokenDto.setCode(code);
        tokenDto.setRedirect_uri(redirectUri);
        tokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(tokenDto);
        System.out.println(accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null&&githubUser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            //添加user数据
//            userService.createOrUpdate(user);
            //登录成功显示我的信息 并跳转到首页
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            //登录失败 跳转到首页
            return "redirect:/";
        }

    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        //删除session
        request.getSession().invalidate();
        //删除cookie
        Cookie cookie = new Cookie("token",null);
        //立即删除
        cookie.setMaxAge(0);
        //任何目录
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }
}
