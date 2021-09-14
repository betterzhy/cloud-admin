package com.zhy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhy.dto.UserQueryCriteria;
import com.zhy.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    IPage<User> selectUserByCriteria(IPage page, UserQueryCriteria criteria);
}
