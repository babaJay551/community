package com.wujie.community.service;

import com.wujie.community.dto.CommentDto;
import com.wujie.community.enums.CommentTypeEnum;
import com.wujie.community.enums.NotificationStatusEnum;
import com.wujie.community.enums.NotificationTypeEnum;
import com.wujie.community.exception.CustomException;
import com.wujie.community.exception.CustomizeErrorCode;
import com.wujie.community.mapper.*;
import com.wujie.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentor){

        //判断是否选择了提问或评论
        if(comment.getParentId()==null||comment.getParentId()==0){
            //抛出自定异常
            throw new CustomException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        //判断这个提问是否还存在
        if(comment.getType()==null||!CommentTypeEnum.isExist(comment.getType())){
            //抛出自定义异常
            throw new CustomException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        //判断当前的是已经发布了的提问还是评论
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment==null){
                throw new CustomException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }else{

                //回复提问
                Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
                if (question==null){
                    throw new CustomException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }

                //添加回复
                comment.setCommentCount(1);
                commentMapper.insert(comment);
                //需要我们新建一个comment对象
                Comment parentComment = new Comment();
                parentComment.setId(comment.getParentId());
                parentComment.setCommentCount(1);
                //增加评论数
                commentExtMapper.incCommentCount(parentComment);

                //创建通知
                createNotifiy(comment, dbComment.getCommentator(),commentor.getName(),question.getTitle(),NotificationTypeEnum.REPLY_COMMENT, NotificationStatusEnum.UNREAD,question.getId());
            }
        }else{
            //回复提问
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question==null){
                throw new CustomException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }else{
                //添加回复
               question.setCommentCount(1);
               commentMapper.insert(comment);
                //累加回复数
               questionExtMapper.incCommentCount(question);
               //创建通知
                createNotifiy(comment,question.getCreator(),commentor.getName(),question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, NotificationStatusEnum.UNREAD,question.getId());
            }

        }
    }

    private void createNotifiy(Comment comment, Long receiver, String notifierName, String outerTitle,
                               NotificationTypeEnum notificationTypeEnum, NotificationStatusEnum notificationStatusEnum,
                               Long outerid) {
        if (comment.getCommentator()==receiver){
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTypeEnum.getType());
        //回复的是谁也就是question id
        notification.setOuterid(outerid);
        //是谁回复的
        notification.setNotifier(comment.getCommentator());
        //是否已查看
        notification.setStatus(notificationStatusEnum.getStatus());
        //被评论回复的对象
        notification.setReceiver(receiver);
        //回复人的名字
        notification.setNotifierName(notifierName);
        //被回复的内容
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    //获取到所有的question的id集合
    public List<CommentDto> listByTargetId(Long id, CommentTypeEnum commentTypeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        //判断集合是否为空
        if (comments.size()==0){
            return new ArrayList<>();
        }
        //使用lumda 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        //转换为list集合
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment 为commentDTO
        List<CommentDto> commentDTOS = comments.stream().map(comment -> {
            CommentDto commentDTO = new CommentDto();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
