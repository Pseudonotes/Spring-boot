package com.example.demo.config;

import com.example.demo.domain.service.JwtService;
import com.example.demo.domain.service.UsernameUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        LOGGER.info("header {}", header);

        if (header != null && header.startsWith("Bearer ")) {

            var token = header.substring(7);

            LOGGER.info("token {}", token);
            var username = jwtService.extractUsername(token);

            //get security context instance
            SecurityContext context = SecurityContextHolder.getContext();

            //if user is not authenticated
            if (username != null && context.getAuthentication() == null) {

                //fetch user details
                final UserDetails userDetails = applicationContext
                        .getBean(UsernameUserDetailsService.class)//using our service
                        .loadUserByUsername(username);

                //validate token
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken token1 =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //pass request<Http> details
                    token1.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    context.setAuthentication(token1);
                }
            }

        } else LOGGER.info("no token");

        filterChain.doFilter(request, response);
    }
}
