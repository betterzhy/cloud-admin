package com.zhy.service;

import com.zhy.dto.LoginRequest;
import com.zhy.dto.RegisterRequest;
import com.zhy.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * (Admin)表服务接口
 *
 * @author makejava
 * @since 2021-09-13 10:31:33
 */
public interface AdminService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Admin queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Admin> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    Admin insert(Admin admin);

    /**
     * 修改数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    Admin update(Admin admin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    String login(LoginRequest loginRequest, HttpSession session);

    void logout(String token);

    Admin register(RegisterRequest registerRequest);
}