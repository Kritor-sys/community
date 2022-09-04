package com.study.community;

import com.study.community.dao.LoginTicketMapper;
import com.study.community.dao.UserMapper;
import com.study.community.dao.UserMapper;
import com.study.community.entity.LoginTicket;
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

    @Autowired
    private LoginTicketMapper loginTicketMapper;

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
    @Test
    public void selectUserNew(){
        User user = usermapper.selectByName("admin1");
        System.out.println(user.toString());
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket("abc");
        loginTicket.setUserId(101);
        loginTicket.setStatus(1);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+6000*6));
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
        loginTicketMapper.updateStatus("abc",1);
        System.out.println(loginTicket);
    }
}
