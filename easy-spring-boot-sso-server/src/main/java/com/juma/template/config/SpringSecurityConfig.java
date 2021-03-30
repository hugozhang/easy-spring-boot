package com.juma.template.config;

import com.juma.template.sso.CustomAuthenticationProvider;
import com.juma.template.sso.JWTAuthenticationFilter;
import com.juma.template.sso.JWTLoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
//        web.ignoring().antMatchers("/hello","/login");

        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html",
                "/webjars/**");
    }

    public BCryptPasswordEncoder passwordEncoder() {
        // 配置默认的加密方式
        return new BCryptPasswordEncoder();
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
                .antMatchers("/login","/hello","/oauth/authorize","/oauth/token").permitAll()
                // 剩余的其它请求需要身份认证
                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .failureHandler((req, resp, e) -> {
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    out.write("error");
//                    out.flush();
//                    out.close();
//                })
//                .permitAll()
//                //定义登录页面，未登录时，访问一个需要登录之后才能访问的接口，会自动跳转到该页面
////                .loginPage("/login")
//                //登录处理接口
////                .loginProcessingUrl("/doLogin").permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout").permitAll()
                .and()
                //验证登陆
                .addFilterAt(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //验证token
                .addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
//                .accessDeniedHandler(accessDeniedHandler());//处理AccessDeniedException

                ;
//                .and().httpBasic();
    }


    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("请登录");
                response.setStatus(401);
            }
        };
    }


    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandler(){
            public void handle(HttpServletRequest request, HttpServletResponse response,
                               AccessDeniedException accessDeniedException) throws IOException, ServletException {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print("非法请求");
                response.setStatus(403);
            }
        };
    }


}
