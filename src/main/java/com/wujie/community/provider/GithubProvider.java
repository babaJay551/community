package com.wujie.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wujie.community.dto.AccessTokenDto;
import com.wujie.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class GithubProvider {


    //获取access_token post请求
    public String getAccessToken(AccessTokenDto accessTokenDto){

        //获取json格式的数据
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        //okhttp做网络请求
        OkHttpClient client = new OkHttpClient();

            //响应体
            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String token = string.split("&")[0].split("=")[1];
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    //获取user  get请求
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("name","小杰");
            map.put("id","56109466");
            map.put("dio","");
            map.put("avatarUrl","https://avatars0.githubusercontent.com/u/56109466?v=4");

            JSONObject json = new JSONObject(map);
            GithubUser githubUser = JSON.parseObject(String.valueOf(json), GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


