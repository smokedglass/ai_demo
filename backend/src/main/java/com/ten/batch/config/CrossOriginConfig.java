package com.ten.batch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {
    static final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" };
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods(ORIGINS).maxAge(3600);
//        registry.addMapping("/**").allowedOrigins("*").allowedHeaders(CorsConfiguration.ALL).allowedMethods(CorsConfiguration.ALL).maxAge(3600);
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders(CorsConfiguration.ALL).allowedMethods(CorsConfiguration.ALL)
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options").allowCredentials(false).maxAge(3600);
        System.out.println("已执行+++++++++++++++++++");
    }

}
