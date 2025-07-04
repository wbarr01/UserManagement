package com.waltbarr.user.configuration;

import com.waltbarr.user.repository.UserRepository;
import com.waltbarr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


//    @Autowired
//    public SecurityConfig(UUIDTokenFilter uuidTokenFilter){
//        this.uuidTokenFilter = uuidTokenFilter;
//    }

    @Bean
    public UUIDTokenFilter uuidTokenFilter(UserService userService){
        return new UUIDTokenFilter(userService);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,UUIDTokenFilter uuidTokenFilter) throws Exception {
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
