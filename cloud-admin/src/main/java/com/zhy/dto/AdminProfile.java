package com.zhy.dto;

import lombok.Data;

/**
 * 用户简介
 */
@Data
public class AdminProfile {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像地址
     */
    private String avatar;
}
