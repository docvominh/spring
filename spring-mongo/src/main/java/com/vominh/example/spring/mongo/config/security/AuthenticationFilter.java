package com.vominh.example.spring.mongo.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vominh.example.spring.mongo.exception.ResponseError;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final ObjectMapper mapper;

    private final AppUserDetailService userDetailService;

    public static final String TOKEN_NAME = "secret_token";

    public AuthenticationFilter(JwtService jwtService, ObjectMapper mapper, AppUserDetailService userDetailService) {
        this.jwtService = jwtService;
        this.mapper = mapper;
        this.userDetailService = userDetailService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/swagger-ui/**", request.getServletPath()) ||
                new AntPathMatcher().match("/api/user/signup", request.getServletPath()) ||
                new AntPathMatcher().match("/api/user/signin", request.getServletPath()) ||
                new AntPathMatcher().match("/v3/api-docs/**", request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var cookieOpt = getAuthenticatedCookieFromRequest(request);
        String token = "";
        if (cookieOpt.isEmpty()) {
            returnError(response, "No authentication cookie found");
        } else {
            token = cookieOpt.get().getValue();
            if (jwtService.isTokenExpired(token)) {
                returnError(response, "Token is expired");
            }
        }
        if (StringUtils.isNotEmpty(token)) {
            String email = jwtService.getClaimFromToken(token, Claims::getSubject);
            updateSecurityContext(email, request);
            filterChain.doFilter(request, response);
        }

    }

    private void returnError(HttpServletResponse response, String message) throws IOException {
        var error = ResponseError.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .code(HttpStatus.FORBIDDEN.getReasonPhrase())
                .message(message)
                .details(Collections.emptyList())
                .build();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(error));
    }

    private void updateSecurityContext(String email, HttpServletRequest httpServletRequest) {
        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Optional<Cookie> getAuthenticatedCookieFromRequest(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Stream.of(request.getCookies()).filter(c -> c.getName().equals(TOKEN_NAME)).findAny();
        }

        return Optional.empty();
    }
}
