/*package com.example.student.config;

import com.example.student.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    // 1. Khai báo "Máy mã hóa mật khẩu" (Bean này cực kỳ quan trọng)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Cấu hình FilterChain: Định nghĩa lối ra vào
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Tắt CSRF vì mình làm REST API
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Cho phép đăng nhập tự do
                .requestMatchers("/h2-console/**").permitAll() // Cho phép vào xem Database
                .anyRequest().authenticated() // Mọi thứ khác phải đăng nhập
            )
            // Cấu hình để xem được giao diện H2 Console
            .headers(headers -> headers.frameOptions(f -> f.disable())) 
            // Dùng form đăng nhập mặc định để test bước đầu
            .formLogin(form -> form.permitAll());

        return http.build();
    }

    // 3. Cấu hình người thợ kiểm tra đăng nhập (AuthenticationProvider)



@Bean
    public AuthenticationProvider authenticationProvider() {
        
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(myUserDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
    */