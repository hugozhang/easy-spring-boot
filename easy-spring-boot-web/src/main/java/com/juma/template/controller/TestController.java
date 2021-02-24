package com.juma.template.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"测试模块"})
@RestController
//@RequestMapping("test")
public class TestController {

    @ResponseBody
    @GetMapping("/")
    public void test() {

        System.out.println(123);

    }


    @ResponseBody
    @GetMapping("/login")
    public void login(String username,String password) {

        System.out.println(123);

    }

    @ResponseBody
    @GetMapping("/hello")
    public void hello() {

        System.out.println("hello");

    }

}
