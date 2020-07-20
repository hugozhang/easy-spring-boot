package com.juma.template.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"测试模块"})
@RestController
@RequestMapping("test")
public class TestController {

    @ResponseBody
    @PostMapping("/")
    public void test() {

    }

}
