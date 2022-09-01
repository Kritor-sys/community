package com.study.community.dao;

import com.study.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    /**
     * 查询用户
     */
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String Email);

    /**
     * 增加用户
     */
    int insertUser(User user);
    /**
     * 修改用户
     */
    int updateStatus(int id,int status);

    int updateHeader(int id,String headerUrl);

    int updatePassword(int id,String password);


}
