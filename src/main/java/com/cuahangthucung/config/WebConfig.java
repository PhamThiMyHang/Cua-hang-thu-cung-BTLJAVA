package com.cuahangthucung.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
<<<<<<< HEAD
        registry.addMapping("/**") // Cho phép tất cả các đường dẫn API
                .allowedOriginPatterns("*") // Chấp nhận mọi nguồn (quan trọng để test dễ)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // Cho phép gửi kèm thông tin xác thực
=======
        // Cho phép tất cả các API trong project nhận yêu cầu từ Frontend
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // URL của project Next.js
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
>>>>>>> DoanThiNgocGiau
    }
}