package com.study.community;

import com.study.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTests{

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void sensitiveFilterTest(){
        String s = "可以嫖娼,可以吸毒,可以开票,哈哈哈";
        s = sensitiveFilter.filter(s);
        System.out.println(s);
    }
}
