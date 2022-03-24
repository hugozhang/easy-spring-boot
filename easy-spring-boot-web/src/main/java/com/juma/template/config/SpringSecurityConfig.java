package com.juma.template.config;

import com.juma.template.sso.CustomAuthenticationProvider;
import com.juma.template.sso.JWTAuthenticationFilter;
import com.juma.template.sso.JWTLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: hugo.zxh
 * @Date: 2020/12/02 16:03
 * @Description:
 */
//@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private UserDetailsService userDetailsService;


    //该地址不走 Spring Security 过滤器链
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        // 使用自定义身份验证组件
        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService,new BCryptPasswordEncoder()));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //开启登录配置
                .authorizeRequests()
                //允许以下请求
                .antMatchers("/hello","/login").permitAll()

                // 剩余的其它请求需要身份认证
                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                //定义登录页面，未登录时，访问一个需要登录之后才能访问的接口，会自动跳转到该页面
////                .loginPage("/login")
//                //登录处理接口
////                .loginProcessingUrl("/doLogin").permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout").permitAll()
                .and()
                //验证登陆
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //验证token
                .addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
                ;
//                .and().httpBasic();
    }


}
