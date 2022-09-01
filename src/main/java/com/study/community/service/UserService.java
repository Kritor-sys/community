package com.study.community.service;

import com.study.community.dao.UserMapper;
import com.study.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id)
    {
        return userMapper.selectById(id);
    }
}
