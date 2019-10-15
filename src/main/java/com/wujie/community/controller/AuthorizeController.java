package com.wujie.community.controller;

import com.wujie.community.dto.AccessTokenDto;
import com.wujie.community.dto.GithubUser;
import com.wujie.community.mapper.UserMapper;
import com.wujie.community.model.User;
import com.wujie.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientid;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

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
        System.out.println("----");
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            System.out.println(user.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            System.out.println(user.getGmtCreate());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());

            //添加user数据
            userMapper.insert(user);
            //登录成功显示我的信息 并跳转到首页
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            //登录失败 跳转到首页
            return "redirect:/";
        }

    }
}
