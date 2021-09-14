package com.zhy.dto;

import com.zhy.commons.constants.PageConstants;
import lombok.Data;

@Data
public class UserQueryCriteria {
    private Integer pageNum = PageConstants.DEFAULT_PAGE_NUM;

    private Integer pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    private Integer id;

    private String name;

    private String phone;
}
