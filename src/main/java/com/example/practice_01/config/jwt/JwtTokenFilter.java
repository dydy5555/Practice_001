package com.example.practice_01.config.jwt;

import com.example.practice_01.model.UserApp;
import com.example.practice_01.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthService authService;

    @Autowired
    public JwtTokenFilter(JwtUtils jwtUtils,@Lazy AuthService authService) {
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            /* final String authHeader = request.getHeader("Authorization");
            String gmail = null;
            String phoneNumber = null;
            String provider = null;
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                try {
                    gmail = jwtTokenUtil.getGmailFromToken(token);
                    phoneNumber = jwtTokenUtil.getPhoneNumberFromToken(token);
                    provider = jwtTokenUtil.getProviderFromToken(token);
                } catch (IllegalArgumentException | ExpiredJwtException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
            // Check if the user is authenticated
            if (gmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Users user = userService.getUserDetails(phoneNumber,provider);
                if (jwtTokenUtil.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            chain.doFilter(request, response);*/
            final String authHeader = request.getHeader("Authorization");
            String gmail = null;
            String phoneNumber = null;
            String provider = null;
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                try {
                    gmail = jwtUtils.getGmailFromToken(token);
                    phoneNumber = jwtUtils.getPhoneNumberFromToken(token);
                    provider = jwtUtils.getProviderFromToken(token);
                } catch (IllegalArgumentException | ExpiredJwtException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
            // Check if the user is authenticated
            if (gmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserApp user = authService.getUserDetails(phoneNumber,provider);
                if (jwtUtils.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);


    }
//    private String parseJwt(HttpServletRequest request){
//        String headerAuth = request.getHeader("Authorization");
//        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
//            return headerAuth.substring(7, headerAuth.length());
//        }
//        return null;
//    }
}
