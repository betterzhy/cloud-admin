package com.zhy.dto;

import com.zhy.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryTree {
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父级分类id
     */
    private Integer parentId;
    /**
     * 子级分类
     */
    private List<Category> children;
}
