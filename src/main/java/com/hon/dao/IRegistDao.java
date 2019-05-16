package com.hon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hon.entity.User;
import org.apache.ibatis.annotations.Update;

public interface IRegistDao extends BaseMapper<User> {

    @Update("updatePWD")
    public int updatePWD(User user);
}
