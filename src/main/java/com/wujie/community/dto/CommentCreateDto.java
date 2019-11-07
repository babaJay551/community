package com.wujie.community.dto;

import lombok.Data;

//回复文本内容DTO类
@Data
public class CommentCreateDto {

    private long parentId;
    private String content;
    private int type;
}
