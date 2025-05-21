package com.api_gateway.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.api_gateway.security.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JWTFilter implements Filter {

    @Autowired
    JWTService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	
            chain.doFilter(request, response); // Proceed (let Spring Security handle auth failure)
            return;
        }

        try {
            System.out.println("API Gateway received JWT: " + authHeader);
            String token = authHeader.substring(7);
            Claims claims = jwtService.parseToken(token);

            if (claims != null) {
                String role = claims.get("role", String.class);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                claims.getSubject(),
                                null,
                                Collections.singleton(new SimpleGrantedAuthority(role))
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (JwtException e) {
            // Invalid token - clear context and proceed (Spring Security will block if needed)
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}