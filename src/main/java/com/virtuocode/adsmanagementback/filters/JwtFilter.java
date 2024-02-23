package com.virtuocode.adsmanagementback.filters;

import com.virtuocode.adsmanagementback.entities.User;
import com.virtuocode.adsmanagementback.services.UserService.UserService;
import com.virtuocode.adsmanagementback.services.jwtService.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

//    private final AuthService authService;

//    private final List<> ALLOWED_PATH = "/api/auth/login" || "/api/auth/register";
    public JwtFilter(JwtService jwtService
            , UserService userService
//            , AuthService authService
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
//        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = extractTokenFromRequestAndCheckIfValid(request);
        if (token == null) {
            handleInvalidOrMissingToken(response);
            return;
        }

        User user = jwtService.getUserFromToken(token);
        if (jwtService.checkTokenExpiration(user)) {
            userService.updateLastActivity(user);

            //authenticating user
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));

            filterChain.doFilter(request, response);
        } else {
            handleExpiredToken(response);
        }
    }

    private void handleInvalidOrMissingToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Invalid or missing token");
    }

    private void handleExpiredToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Token expired, please get a new token");
    }


    private String extractTokenFromRequestAndCheckIfValid(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            if (jwtService.validateToken(token)) {
//                if(jwtService.checkTokenExpiration(jwtService.getUserFromToken(token))){
//                    return token;
//                }
                return token;

            }
        }
        return null;
    }


}
