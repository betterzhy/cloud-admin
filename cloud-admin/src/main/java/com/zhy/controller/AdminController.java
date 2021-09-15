package com.zhy.controller;

import com.zhy.commons.model.ApiResponse;
import com.zhy.dto.AdminProfile;
import com.zhy.dto.LoginRequest;
import com.zhy.dto.RegisterRequest;
import com.zhy.entity.Admin;
import com.zhy.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zhy
 */
@RefreshScope
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService adminService;

    /**
     * @param loginRequest 登录请求体
     * @return token
     */
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody @Validated LoginRequest loginRequest, HttpSession session) {
        String token = adminService.login(loginRequest, session);
        return ApiResponse.ok("login successful", token);
    }

    /**
     * @param token
     * @return
     */
    @PostMapping("/logout/{token}")
    public ApiResponse<?> logout(@PathVariable("token") String token) {
        adminService.logout(token);
        return ApiResponse.ok("logout successful");
    }

    /**
     * 用户注册
     *
     * @param registerRequest 注册参数
     * @return 单条数据
     */
    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody @Validated RegisterRequest registerRequest) {
        Admin admin = this.adminService.register(registerRequest);
        AdminProfile adminProfile = new AdminProfile();
        BeanUtils.copyProperties(admin, adminProfile);
        return ApiResponse.ok(adminProfile);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/queryAdminProfileById")
    public ApiResponse<?> queryAdminProfileById(Integer id) {
        Admin admin = this.adminService.queryById(id);
        AdminProfile adminProfile = new AdminProfile();
        BeanUtils.copyProperties(admin, adminProfile);
        return ApiResponse.ok(adminProfile);
    }

}
