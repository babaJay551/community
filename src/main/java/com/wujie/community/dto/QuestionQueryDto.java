package com.wujie.community.dto;

import lombok.Data;

@Data
public class QuestionQueryDto {

    private String search;
    private Integer offset;
    private Integer pageSize;
}
