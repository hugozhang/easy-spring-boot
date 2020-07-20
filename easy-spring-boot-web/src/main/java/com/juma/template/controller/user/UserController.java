package com.juma.template.controller.user;

import com.juma.template.user.User;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"用户模块"})
@RestController
@RequestMapping("user")
public class UserController {

    @ResponseBody
    @GetMapping("/")
    public User get(){
        User user = new User();
        user.setUsername("hello spring boot");
        return user;
    }

}
