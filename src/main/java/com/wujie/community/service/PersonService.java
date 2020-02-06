package com.wujie.community.service;


import com.wujie.community.dto.PersonDto;
import com.wujie.community.mapper.PersonMapper;
import com.wujie.community.model.Person;
import com.wujie.community.model.PersonExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonMapper personMapper;

    /*
       登录的方法
    */
    public boolean personLogin(PersonDto personDto,String imgCode){
        //如果查询的user用户为空
        PersonExample personExample = new PersonExample();
        personExample.createCriteria().andUsernameEqualTo(personDto.getUsername());
        List<Person> personList = personMapper.selectByExample(personExample);
        if (imgCode.equals(personDto.getCode())){
            if (personList.size()>0){
                return true;
            }
        }
        return false;
    }

    /*
     * 更新或添加方法
     * */
    public void createOrUpdate(Person person) {
        //如果查询的user用户为空
        PersonExample personExample = new PersonExample();
        personExample.createCriteria().andUsernameEqualTo(person.getUsername());
        List<Person> personList = personMapper.selectByExample(personExample);

        if (personList.size() == 0) {
            //对用户进行添加操作
            personMapper.insert(person);
        } else {
            //对用户进行最新更新操作
            //原数据库中存在的user对象
            Person dbPerson = personList.get(0);
            //进行更新的user对象
            Person updatePerson = new Person();
            updatePerson.setPassword(person.getPassword());
            updatePerson.setName(person.getName());
            updatePerson.setAvatarUrl(person.getAvatarUrl());

            PersonExample example = new PersonExample();
            example.createCriteria().andIdEqualTo(dbPerson.getId());
            //更新
            personMapper.updateByExampleSelective(updatePerson, example);
        }
    }
}
