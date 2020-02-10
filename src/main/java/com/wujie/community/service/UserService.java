package com.wujie.community.service;


import com.wujie.community.dto.UserDto;
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
       登录的方法
    */
    public User userLogin(UserDto userDto, String imgCode){
        //如果查询的user用户为空
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(userDto.getUsername())
        .andPasswordEqualTo(userDto.getPassword());
        List<User> userList = userMapper.selectByExample(userExample);
        if (imgCode.equalsIgnoreCase(userDto.getCode())){
            if (userList.size()>0){
                return userList.get(0);
            }
        }
        return new User();
    }

    /*
     * 更新或添加方法
     * */
    public void createOrUpdate(User user){
        //如果查询的user用户为空
        if (user.getId()==null){
            //对用户进行添加操作
            userMapper.insert(user);
        } else {
            //对用户进行最新更新操作
            //进行更新的user对象
            User updateUser = new User();
            updateUser.setUsername(user.getUsername());
            updateUser.setPassword(user.getPassword());
            updateUser.setSex(user.getSex());
            updateUser.setName(user.getName());
            updateUser.setAvatarUrl(user.getAvatarUrl());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(user.getId());
            //更新
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }

    /**
     * 根据id查询
     *
     */

    public User selectOne(Long id){
        //如果查询的user用户为空
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);
        List<User> userList = userMapper.selectByExample(userExample);
        return userList.get(0);
    }
}
