package com.zhy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhy.commons.model.PagedResponse;
import com.zhy.dao.UserMapper;
import com.zhy.dto.UserQueryCriteria;
import com.zhy.entity.User;
import com.zhy.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户(User)表服务实现类
 *
 * @author makejava
 * @since 2021-09-13 21:56:49
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public PagedResponse<User> getUserList(UserQueryCriteria criteria) {
        // 查询参数
        Integer pageNum = criteria.getPageNum();
        Integer pageSize = criteria.getPageSize();
        Integer id = criteria.getId();
        String name = criteria.getName();
        String phone = criteria.getPhone();

        // 查询语句构建
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (name != null){
            wrapper.like("name", name);
        }
        if (phone != null){
            wrapper.like("phone", phone);
        }

        // 执行查询
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        return new PagedResponse<User>(
                userPage.getRecords(),
                userPage.getCurrent(),
                userPage.getSize(), userPage.getTotal(),
                userPage.getPages(),
                userPage.getCurrent() == userPage.getPages()
        );
    }
}