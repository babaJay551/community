package com.wujie.community.controller;

import com.wujie.community.dto.CommentDto;
import com.wujie.community.dto.QuesionDto;
import com.wujie.community.enums.CommentTypeEnum;
import com.wujie.community.service.CommentService;
import com.wujie.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    //根据id查询问题的详细信息
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Long id,
                           Model model){

        //根据question_id查找
        QuesionDto quesionDto = questionService.findQusById(id);
        //查询相关问题
        List<QuesionDto> relatedQuestions = questionService.selectRelated(quesionDto);
        //根据发布问题类型 进行类别查询
        List<CommentDto> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",quesionDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
