package com.juma.template.controller;

import com.juma.template.config.CustomerConfig;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = {"测试模块"})
@RestController
//@RequestMapping("test")
public class TestController {


    @Resource
    private CustomerConfig customerConfig;

    @ResponseBody
    @GetMapping("/test")
    public void test() {

        System.out.println(customerConfig.getSign());

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
        System.out.println(customerConfig.getSign());
        System.out.println("hello");

    }

}
