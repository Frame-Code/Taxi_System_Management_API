package com.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.frame_code.domain.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@CommonsLog
public class JwtUtils {
    @Value("${security.jwt.secret-key}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    @Value("${security.jwt.expiration}")
    private long expirationToken;

    public Optional<String> createToken(@NotNull Authentication authentication, String owner) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return buildToken(username, authorities, owner, algorithm, expirationToken);

    }

    private Optional<String> buildToken(String username, String authorities, String owner, Algorithm algorithm, long expirationToken) {
        try {
            return Optional.of(JWT.create()
                    .withIssuer(userGenerator)
                    .withSubject(username)
                    .withClaim("authorities", authorities)
                    .withClaim("username", username)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationToken))
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(algorithm)
            );
        } catch (JWTCreationException ex) {
            log.error("Error building token, invalid configuration or claims");
            throw ex;
        }
    }

    public DecodedJWT validateToken(@NotNull final String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(userGenerator)
                .build();

        return verifier.verify(token);
    }

    public String getUsername(@NotNull final DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public Claim getSpecificClaim(@NotNull final DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    public boolean isValidToken(@NotNull final DecodedJWT decodedJWT, User user) {
        String email = getUsername(decodedJWT);
        return user.getEmail().equals(email) && !isTokenExpired(decodedJWT);
    }

    public boolean isTokenExpired(@NotNull final DecodedJWT decodedJWT) {
        return decodedJWT.getExpiresAt().before(new Date());
    }


}
