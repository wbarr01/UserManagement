package com.waltbarr.user.configuration;

import com.waltbarr.user.DTO.UserResponseDTO;
import com.waltbarr.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UUIDTokenFilter extends OncePerRequestFilter {

    private static final List<String> noAuthList= List.of("/api/users/register", "/h2-console","/h2-console/");

    private final UserService userService;

    public UUIDTokenFilter(UserService userService)
    {
        this.userService = userService;
    }
    /**
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if(noAuthList.contains(path)){
            filterChain.doFilter(request,response);
            return;
        }

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            writeErrorResponse(response,"token inválido o faltante");
            return;
        }

        String token = header.substring(7);
        Optional<UserResponseDTO> userOpt = userService.findByToken(token);
        if(userOpt.isEmpty()){
            writeErrorResponse(response,"token inválido");
            return;
        } else if( !userOpt.get().getTokenExpiration().isAfter(LocalDateTime.now())){

            writeErrorResponse(response,"token expirado");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(userOpt.get(),null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }

    private void writeErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"mensaje\": \"" + message + "\"}");
    }
}
