package com.waltbarr.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UUIDTokenFilter uuidTokenFilter;

    public SecurityConfig(UUIDTokenFilter uuidTokenFilter){
        this.uuidTokenFilter = uuidTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(uuidTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();// h2-console

        return http.build();
    }
}
