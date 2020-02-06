package com.wujie.community.service;

import com.wujie.community.mapper.UserMapper;
import com.wujie.community.model.User;
import com.wujie.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;



    /*
    * 更新或添加方法
    * */
    public void createOrUpdate(User user) {

        //如果查询的user用户为空
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> userList = userMapper.selectByExample(userExample);

        if (userList.size()==0){
            //对用户进行添加操作
            userMapper.insert(user);
        }else {
            //对用户进行最新更新操作
            //原数据库中存在的user对象
            User dbuser = userList.get(0);
            //进行更新的user对象
            User updateUser = new User();
            updateUser.setToken(user.getToken());
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setName(user.getName());
            updateUser.setAvatarUrl(user.getAvatarUrl());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbuser.getId());
            //更新
            userMapper.updateByExampleSelective(updateUser, example);
        }

    }



}
