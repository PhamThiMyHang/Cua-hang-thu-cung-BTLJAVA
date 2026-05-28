package com.cuahangthucung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Bắt buộc phải có để Spring Boot nhận diện khi quét component
@EnableWebSecurity // Kích hoạt tính năng tùy biến Security cho Web
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Tắt CSRF theo cú pháp chuẩn của Spring Boot 3.x / Security 6.x
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Mở cửa hoàn toàn cho tất cả request, không chặn bất kỳ API nào
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 3. Vô hiệu hóa tính năng đăng nhập mặc định (Form login và Basic Auth)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}