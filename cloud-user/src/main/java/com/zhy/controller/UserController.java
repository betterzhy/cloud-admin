package com.zhy.controller;

import com.zhy.commons.ApiResponse;
import com.zhy.dto.UserQueryCriteria;
import com.zhy.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户(User)表控制层
 *
 * @author makejava
 * @since 2021-09-13 21:56:50
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param criteria 查询条件
     * @return 用户列表
     */
    @PostMapping("/getUserList")
    public ApiResponse<?> getUserList(@RequestBody UserQueryCriteria criteria) {
        return ApiResponse.ok(this.userService.getUserList(criteria));
    }

}