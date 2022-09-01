package com.study.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class testController {

    @ResponseBody
    @RequestMapping("/hello")
    public String sayHello(){
        return "hello springboot";
    }
}
