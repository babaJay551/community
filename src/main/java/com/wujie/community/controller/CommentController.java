package com.wujie.community.controller;

import com.wujie.community.dto.CommentCreateDto;
import com.wujie.community.dto.CommentDto;
import com.wujie.community.dto.ResultDto;
import com.wujie.community.enums.CommentTypeEnum;
import com.wujie.community.exception.CustomizeErrorCode;
import com.wujie.community.model.Comment;
import com.wujie.community.model.User;
import com.wujie.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    //回复提问 返回一个json对象 post请求
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentDTO,
                       HttpServletRequest request){

        //创建文本对象
        Comment comment = new Comment();

        //获取session中的user对象
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        //判断用户的回复内容是否为空
        if (commentDTO==null|| StringUtils.isBlank(commentDTO.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        //设置文本属性值
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(1);

        //调用文本内容插入的方法
        commentService.insert(comment,user);

        return ResultDto.okOf();
    }

    //二级评论
    @ResponseBody
    @GetMapping("/comment/{id}")
    public ResultDto<List> comments(@PathVariable Long id){
        List<CommentDto> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDto.okOf(commentDTOS);
    }
}
