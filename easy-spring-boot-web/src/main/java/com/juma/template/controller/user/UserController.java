package com.juma.template.controller.user;

import com.juma.template.common.valid.OnCreate;
import com.juma.template.common.valid.OnUpdate;
import com.juma.template.exception.BizServiceException;
import com.juma.template.user.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * https://yangbingdong.com/2018/spring-boot-mvc-validation/
 */

@Api(tags = {"用户模块"})
@Validated
@RestController
@RequestMapping("user")
public class UserController {

    @Value("${spring.zipkin.kafka.topic:zipkin}")
    private String topic;

    @GetMapping("/")
    public User get() {
        User user = new User();
        user.setUsername("hello spring boot");
        System.out.println(topic);
        return user;
    }

    /**
     * 加了@NotBlank  请求参数形式是json body?
     * @param age
     */
    @Validated
    @GetMapping("/error2")
    public void error2(@NotNull Integer  age) {
        System.out.println(age);
    }

    @GetMapping("/error")
    public User error() {
        throw new BizServiceException("user.not.found","用户不存在");
    }


    @PutMapping("/group/create")
    public void create(@Validated(OnCreate.class) @RequestBody User user) {

    }

    @PutMapping("/group/update")
    public void update(@Validated(OnUpdate.class) @RequestBody User user) {

    }

    /**
     * 表单参数验证
     * @param user
     * @return
     */
    @PutMapping("/put")
    public User put(@Valid User user) {
        return user;
    }

    /**
     * json body参数验证
     * @param user
     * @return
     */
    @PutMapping("/put2")
    public User put2(@RequestBody @Valid User user) {
        return user;
    }

    /**
     * List参数验证  这种是特殊情况  需要扩展
     * @param userList
     * @return
     */
    @PutMapping("/put3")
    public List<User> put3(@Valid @RequestBody List<User> userList) {
        return userList;
    }

}
