package com.juma.template;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication
//namespace必须配置在EnableNacosConfig中才能自动刷新
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "192.168.90.57:8848", namespace = "bd2e4b4a-db62-4423-b2ca-efdc9ea67218"))
@NacosPropertySource(dataId = "hmap", groupId = "dev", autoRefreshed = true)
@ComponentScan(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);

    }

}
