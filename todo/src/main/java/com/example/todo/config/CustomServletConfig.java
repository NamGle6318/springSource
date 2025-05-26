package com.example.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 라이브 서버 주소
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE") // 허용할 메서드
                .maxAge(300)
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
    }
}
