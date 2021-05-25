package com.juma.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: hugo.zxh
 * @Date: 2020/12/02 16:03
 * @Description:
 */
@Configuration
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {


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
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers().antMatchers("/oauth/**","/login/**","/logout/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .formLogin().permitAll();
    }



}
