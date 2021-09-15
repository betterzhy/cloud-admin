package com.zhy.service.impl;

import com.zhy.commons.exception.ApiException;
import com.zhy.commons.constant.RedisPrefix;
import com.zhy.dao.AdminDao;
import com.zhy.dto.LoginRequest;
import com.zhy.dto.RegisterRequest;
import com.zhy.entity.Admin;
import com.zhy.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * (Admin)表服务实现类
 *
 * @author makejava
 * @since 2021-09-13 10:31:33
 */
@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Admin queryById(Integer id) {
        return this.adminDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Admin> queryAllByLimit(int offset, int limit) {
        return this.adminDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    @Override
    public Admin insert(Admin admin) {
        this.adminDao.insert(admin);
        return admin;
    }

    /**
     * 修改数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    @Override
    public Admin update(Admin admin) {
        this.adminDao.update(admin);
        return this.queryById(admin.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.adminDao.deleteById(id) > 0;
    }

    @Override
    public String login(LoginRequest loginRequest, HttpSession session) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Admin admin = this.adminDao.queryByUsername(username);
        if (admin == null){
            throw new ApiException("username doesn't exists!");
        }
        if (!password.equals(admin.getPassword())){
            throw new ApiException("username or password is not correct!");
        }
        String token = session.getId();
        redisTemplate.opsForValue().set(RedisPrefix.TOKEN_KEY + token, admin, 60, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(RedisPrefix.TOKEN_KEY + token);
    }

    @Override
    public Admin register(RegisterRequest registerRequest) {
        if (adminDao.queryByUsername(registerRequest.getUsername()) != null){
            throw new ApiException("username is unavailable!");
        }
        Admin admin = new Admin();
        BeanUtils.copyProperties(registerRequest, admin);
        adminDao.insert(admin);
        return admin;
    }
}