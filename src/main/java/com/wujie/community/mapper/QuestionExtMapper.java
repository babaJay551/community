package com.wujie.community.mapper;

import com.wujie.community.dto.QuestionQueryDto;
import com.wujie.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    //记录浏览数
    void incView(Question question);
    //记录评论数
    void incCommentCount(Question question);
    //查找相关问题
    List<Question> selectByRelated(Question question);

    Integer countBySearch(QuestionQueryDto questionQueryDto);

    List<Question> selectBySearchWithRowbounds(QuestionQueryDto questionQuery);
}