package com.wujie.community.dto;

import com.wujie.community.model.User;
import lombok.Data;

@Data
public class QuesionDto {

    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
