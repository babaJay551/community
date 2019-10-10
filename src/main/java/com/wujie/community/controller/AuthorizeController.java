package com.wujie.community.controller;

import com.wujie.community.dto.AccessTokenDto;
import com.wujie.community.dto.GithubUser;
import com.wujie.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){

        AccessTokenDto tokenDto = new AccessTokenDto();
        tokenDto.setClient_id(clientid);
        tokenDto.setClient_secret(clientSecret);
        tokenDto.setCode(code);
        tokenDto.setRedirect_uri(redirectUri);
        tokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(tokenDto);
        System.out.println(accessToken);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
