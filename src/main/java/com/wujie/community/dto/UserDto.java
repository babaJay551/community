package com.wujie.community.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String username;
    private String password;
    private Integer sex;
    private String code;
    private String avatarUrl;
}
