package com.wujie.community.service;

import com.wujie.community.dto.PaginationDto;
import com.wujie.community.dto.QuesionDto;
import com.wujie.community.mapper.QuestionMapper;
import com.wujie.community.mapper.UserMapper;
import com.wujie.community.model.Question;
import com.wujie.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDto list(Integer currentPage, Integer pageSize){

        PaginationDto paginationDto =new PaginationDto();
        //查询question总记录数
        Integer totalCount = questionMapper.findCount();
        //需要一个非业务层的方法来 获取想要的参数 例如：totalPage总页数
        paginationDto.setPagination(totalCount,currentPage,pageSize);

        if (currentPage<1){
            currentPage=1;
        }
        if (currentPage>paginationDto.getTotalPage()){
            currentPage=paginationDto.getTotalPage();
        }

        //通过页面传递过来的 当前页码 与页大小
        Integer offset = (currentPage-1)*pageSize;

        //查询所有的question
        List<Question> questionList = questionMapper.findAll(offset,pageSize);
        List<QuesionDto> quesionDtoList = new ArrayList<>();

        for (Question question : questionList) {
            //通过question表关联的creator查询user
            User user = userMapper.findById(question.getCreator());
            QuesionDto quesionDto = new QuesionDto();
            //将左边的字段全部赋值到右边字段表
            BeanUtils.copyProperties(question,quesionDto);
            quesionDto.setUser(user);
            quesionDtoList.add(quesionDto);
        }
        paginationDto.setQuesionDtoList(quesionDtoList);

        return paginationDto;
    }
}
