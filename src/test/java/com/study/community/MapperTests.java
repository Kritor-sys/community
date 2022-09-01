package com.study.community;

import com.study.community.dao.UserMapper;
import com.study.community.dao.UserMapper;
import com.study.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private UserMapper usermapper;

    @Test
    public void testSelectUser(){
        User user = usermapper.selectById(101);
        System.out.println(user);
        user = usermapper.selectByName("liubei");
        System.out.println(user);
        user  = usermapper.selectByEmail("nowcoder13@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("hh");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("etst@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());
        int rows = usermapper.insertUser(user);
        System.out.println(rows);
    }

    @Test
    public void testUpdateUser(){
        int rows = usermapper.updateStatus(150, 1);
        System.out.println(rows);
        rows  = usermapper.updateHeader(150,"http://www.nowcoder.com/102.png");
        System.out.println(rows);
        rows = usermapper.updatePassword(150,"12345678");
        System.out.println(rows);
    }
}
