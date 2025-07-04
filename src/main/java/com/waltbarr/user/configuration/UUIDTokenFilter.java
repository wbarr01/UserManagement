package com.waltbarr.user.configuration;

import com.waltbarr.user.DTO.UserResponseDTO;
import com.waltbarr.user.entities.User;
import com.waltbarr.user.repository.UserRepository;
import com.waltbarr.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//@Component
public class UUIDTokenFilter extends OncePerRequestFilter {

    private final UserService userService;

    //@Autowired
    public UUIDTokenFilter(UserService userService)
    {
        this.userService = userService;
    }    /**
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            UserResponseDTO user = userService.findByToken(token);
            if(user != null && user.getTokenExpiration().isAfter(LocalDateTime.now())){

                Authentication authentication = new UsernamePasswordAuthenticationToken(user,null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
