package com.zhy.dto;

import lombok.Data;

@Data
public class CategoryDto {
    /**
     * 名称
     */
    private String name;
    /**
     * 父级分类id
     */
    private Integer parentId;
}
