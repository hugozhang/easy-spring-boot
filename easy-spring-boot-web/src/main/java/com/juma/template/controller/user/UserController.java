package com.juma.template.controller.user;

import com.juma.template.user.User;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"用户模块"})
@Validated
@RestController
@RequestMapping("user")
public class UserController {

    @ResponseBody
    @GetMapping("/")
    public User get() {
        User user = new User();
        user.setUsername("hello spring boot");
        return user;
    }

    /**
     * 表单参数验证
     * @param user
     * @return
     */
    @ResponseBody
    @PutMapping("/put")
    public User put(@Valid User user) {
        return user;
    }

    /**
     * json body参数验证
     * @param user
     * @return
     */
    @ResponseBody
    @PutMapping("/put2")
    public User put2(@RequestBody @Valid User user) {
        return user;
    }

    /**
     * List参数验证  这种是特殊情况  需要扩展
     * @param userList
     * @return
     */
    @ResponseBody
    @PutMapping("/put3")
    public List<User> put3(@Valid @RequestBody List<User> userList) {
        return userList;
    }

}
