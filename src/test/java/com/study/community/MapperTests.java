package com.study.community;

import com.study.community.dao.LoginTicketMapper;
import com.study.community.dao.MessageMapper;
import com.study.community.dao.UserMapper;
import com.study.community.dao.UserMapper;
import com.study.community.entity.LoginTicket;
import com.study.community.entity.Message;
import com.study.community.entity.User;
import com.study.community.service.UserService;
import com.study.community.util.CommunityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    private UserMapper usermapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testSelectUser(){
        User user = usermapper.selectById(153);
//        String s = CommunityUtil.MD5(user.getPassword() + user.getSalt());
//        user.setPassword(s);
        userService.updatePassword(user.getId(),CommunityUtil.MD5("12345678"+user.getSalt()));
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
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("1bad73eb795f4798b9b7848812f96c17");
        System.out.println(loginTicket);
        loginTicketMapper.updateStatus("1bad73eb795f4798b9b7848812f96c17",0);
        System.out.println(loginTicket);
    }

    @Test
    public void selectMessageMapper(){
        List<Message> messages = messageMapper.selectConversations(111, 0, 20);
        for (Message message:messages){
            System.out.println(messages);
        }
        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);
        List<Message> list = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message:list){
            System.out.println(messages);
        }
        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);
        messageMapper.selectLetterUnreadCount(131,"111_131");

    }
}


