package com.zhy.dto;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * 用户详细信息
 */
@Data
public class AdminInfo {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户头像地址
     */
    private String avatar;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;
    /**
     * 创建用户
     */
    private String createdBy;
    /**
     * 更新用户
     */
    private String updatedBy;
}
