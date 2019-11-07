package com.wujie.community.dto;

import com.wujie.community.model.User;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Integer commentCount;
    private User user;
}
