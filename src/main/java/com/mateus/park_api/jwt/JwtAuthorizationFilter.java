package com.mateus.park_api.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);


        if (token == null || !token.startsWith(JwtUtils.JWT_BEARER)) {
            log.info("JWT token está nulo, vazio ou não iniciado com Bearer.");
            filterChain.doFilter(request, response);
        }

        if (!JwtUtils.isTokenValid(token)) {
            log.warn("JWT token está inválio ou expirado");
            filterChain.doFilter(request, response);
        }

        String userName = JwtUtils.getUserNameFromToken(token);
        toAuthentication(request, userName);

        filterChain.doFilter(request, response);
    }

    private void toAuthentication(HttpServletRequest request, String userName) {
        UserDetails userDetails = detailsService.loadUserByUsername(userName);

        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .authenticated(userDetails, null, userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
