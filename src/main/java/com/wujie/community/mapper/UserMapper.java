package com.wujie.community.mapper;

import com.wujie.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /*
    * 向user表中插入数据
    * */
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    /*
    * 条件查询（token）
    * */
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    /*
    * 条件查询(id)
    * */
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}
