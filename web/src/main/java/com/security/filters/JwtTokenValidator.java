package com.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;

@CommonsLog
@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {
    private final JwtUtils utils;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String token = null;

        if(cookies == null || cookies.length == 0) {
            log.info("No cookies identified on the request");
            filterChain.doFilter(request, response);
            return;
        }

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("access_token")) {
                token = cookie.getValue();
                break;
            }
        }

        if(token == null || token.isBlank()) {
            log.info("No jwt token from the cookies identified, searching on the headers...");
            String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Any authorization token identified");
                filterChain.doFilter(request, response);
                return;
            }

            token = authHeader.substring(7);
        }

        log.info("Jwt token identified...");
        try {
            DecodedJWT decodedJWT = utils.validateToken(token);
            String username = utils.getUsername(decodedJWT);
            String authorities = utils.getSpecificClaim(decodedJWT, "authorities").asString();
            Collection<GrantedAuthority> authoritiesCollection = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authoritiesCollection);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("login saved in spring context");
        } catch (JWTVerificationException ex) {
            log.warn("Error verifying token: " + ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
