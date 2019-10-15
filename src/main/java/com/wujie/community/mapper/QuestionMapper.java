package com.wujie.community.mapper;

import com.wujie.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    /*
    * 向quesion表中插入数据
    * */
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void insertQues(Question question);

    /*
     * 查询question表全部数据
     *  */
    @Select("select * from question limit #{offset},#{pageSize}")
    List<Question> findAll(@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);

    /*
    * 查询总记录数
    * */

    @Select("select count(1) from question")
    Integer findCount();
}
