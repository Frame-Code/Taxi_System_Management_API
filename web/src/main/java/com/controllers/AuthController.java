package com.controllers;

import Enums.entitiesEnums.ROLE_NAME;
import com.security.service.UserDetailsServiceImpl;
import com.security.utils.JwtUtils;
import com.taxi.service.interfaces.user_module.IUserService;
import dto.entities.RoleDto;
import dto.entities.UserDTO;
import dto.http.HttpBaseResponse;
import dto.http.request.LoginUserDto;
import dto.http.request.RegisterUserDto;
import dto.http.response.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.utils.HttpResponseUtils.buildResponse;

@RestController
@RequestMapping("/auth")
@CommonsLog
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsServiceImpl service;
    private final IUserService userService;
    private final PasswordEncoder encoder;
    private final JwtUtils utils;
    @Value("${security.jwt.expiration}")
    private long expirationMs;

    @PostMapping("/log-in")
    public ResponseEntity<HttpBaseResponse> login(
            @RequestBody @NotNull final LoginUserDto login,
            HttpServletResponse httpResponse) {
        try {
            UserDetails details = service.loadUserByUsername(login.email());

            if (!encoder.matches(login.password(), details.getPassword())) {
                return buildResponse("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED, null);
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    login.email(), details.getPassword(), details.getAuthorities());

            Optional<String> token = utils.createToken(auth, details.getUsername());
            if (token.isEmpty()) {
                return buildResponse("Error al generar el token", HttpStatus.INTERNAL_SERVER_ERROR, null);
            }

            setAuthCookie(httpResponse, token.get());
            return buildResponse("Sesión iniciada correctamente", HttpStatus.OK,
                    new TokenResponse(null, details.getUsername()));

        } catch (AuthenticationException ex) {
            log.warn("Error de autenticación: " + ex.getMessage());
            return buildResponse("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED, null);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<HttpBaseResponse> register(
            @RequestBody @NotNull final RegisterUserDto register,
            HttpServletResponse httpResponse) {

        if (!userService.isNewUser(register.email(), register.phone())) {
            log.info("Intento de registro con email ya existente: " + register.email());
            return buildResponse("El correo ya está registrado", HttpStatus.CONFLICT, null);
        }

        Optional<RoleDto> role = userService.findByName(ROLE_NAME.CLIENT);
        if (role.isEmpty()) {
            log.error("Rol CLIENT no encontrado en BD — ejecuta Init_maestros.sql");
            return buildResponse("Error de configuración del sistema", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        String passwordHashed = encoder.encode(register.password());
        UserDTO userDto = userService.save(register, passwordHashed, role.get());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + ROLE_NAME.CLIENT.name()));
        role.get().permissions()
                .forEach(per -> authorities.add(new SimpleGrantedAuthority(per.name())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDto.email(), null, authorities);

        Optional<String> token = utils.createToken(authentication, userDto.getFullNames());
        if (token.isEmpty()) {
            return buildResponse("Error al generar el token", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        setAuthCookie(httpResponse, token.get());

        return buildResponse("Cuenta creada correctamente", HttpStatus.CREATED,
                new TokenResponse(null, userDto.getFullNames()));
    }

    /**
     * Devuelve el email del usuario autenticado.
     * Sirve como ping de sesión: 200 = sesión válida, 401 = sin sesión.
     * La autenticación la verifica Spring Security antes de llegar aquí.
     */
    @GetMapping("/me")
    public ResponseEntity<HttpBaseResponse> me(Authentication auth) {
        return buildResponse("Sesión activa", HttpStatus.OK,
                Map.of("username", auth.getName()));
    }

    @PostMapping("/log-out")
    public ResponseEntity<HttpBaseResponse> logout(HttpServletResponse httpResponse) {
        // maxAge=0 le indica al navegador que elimine la cookie inmediatamente
        Cookie cookie = new Cookie("access_token", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        httpResponse.addCookie(cookie);
        return buildResponse("Sesión cerrada correctamente", HttpStatus.OK, null);
    }

    /**
     * Crea y agrega la cookie access_token con flag HttpOnly.
     * maxAge en segundos = expirationMs / 1000.
     */
    private void setAuthCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("access_token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (expirationMs / 1000));
        // cookie.setSecure(true); // activar cuando la app corra sobre HTTPS
        response.addCookie(cookie);
    }
}
