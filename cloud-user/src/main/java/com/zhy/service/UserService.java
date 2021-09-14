package com.zhy.service;

import com.zhy.commons.PagedResponse;
import com.zhy.dto.UserQueryCriteria;
import com.zhy.entity.User;

/**
 * 用户(User)表服务接口
 *
 * @author makejava
 * @since 2021-09-13 21:56:49
 */
public interface UserService {


    PagedResponse<User> getUserList(UserQueryCriteria criteria);
}