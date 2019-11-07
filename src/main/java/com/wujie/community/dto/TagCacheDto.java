package com.wujie.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagCacheDto {

    private String categoryName;
    private List<String> tags;
}
