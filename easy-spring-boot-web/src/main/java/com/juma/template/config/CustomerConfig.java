package com.juma.template.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: hugo.zxh
 * @Date: 2022/03/22 21:26
 * @Description:
 */
@Configuration
public class CustomerConfig {

    @Value("${afterwards.sign}")
    private  String sign;

    public String getSign() {
        return sign;
    }
}
