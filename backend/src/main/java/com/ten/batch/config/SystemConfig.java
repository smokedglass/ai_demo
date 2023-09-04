package com.ten.batch.config;

import com.ten.batch.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 名称：SystemConfig
 * 功能描述：登录配置类
 *
 * @version 1.0
 * @author: zhangboru
 * @datetime: 2023/08/28 11:14
 */
@Configuration
public class SystemConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1.添加自定义拦截器
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/manage/login");
    }
}
