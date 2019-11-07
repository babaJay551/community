package com.wujie.community.mapper;

import com.wujie.community.model.Comment;

public interface CommentExtMapper {

    void incCommentCount(Comment comment);
}