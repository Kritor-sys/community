package com.study.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 给客户端发送邮件
 */
@Component
public class MailClient {

    //打印日志
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    public void sendMail(String to,String subject,String content){
        try {
            //创建MimeMessage类
            MimeMessage message = mailSender.createMimeMessage();
            //将MimeMessage放进MimeMessageHelper
            MimeMessageHelper helper = new MimeMessageHelper(message);
            //邮件从哪里发送
            helper.setFrom(from);
            //邮件发送给谁
            helper.setTo(to);
            //设置邮件主题
            helper.setSubject(subject);
            //设置文件内容,支持text/html
            helper.setText(content,true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
             logger.error("邮件发送失败:"+e.getMessage());
        }
    }
}
