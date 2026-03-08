package com.controllers;

import com.security.service.UserDetailsServiceImpl;
import com.security.utils.JwtUtils;
import com.taxi.service.interfaces.user_module.IUserService;
import dto.entities.RoleDto;
import dto.entities.UserDTO;
import dto.http.HttpBaseResponse;
import dto.http.request.LoginUserDto;
import dto.http.request.RegisterUserDto;
import dto.http.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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

    @PostMapping("/log-in")
    public ResponseEntity<HttpBaseResponse> login(@RequestBody @NotNull final LoginUserDto login) {
        try{
            UserDetails details = service.loadUserByUsername(login.email());
            if(details == null) {
                return buildResponse("Usuario no registrado", HttpStatus.FORBIDDEN, null);
            }

            if(!encoder.matches(login.password(), details.getPassword())) {
                return buildResponse("Contraseña incorrecta", HttpStatus.FORBIDDEN, null);
            }

            String email = login.email();
            String username = details.getUsername();

            Authentication auth = new UsernamePasswordAuthenticationToken(email, details.getPassword(), details.getAuthorities());
            Optional<String> token = utils.createToken(auth, username);

            return token.map(s ->
                            buildResponse("User login successfully", HttpStatus.OK, new TokenResponse(s, username)))
                    .orElseGet(() ->
                            buildResponse("Error creating token", HttpStatus.INTERNAL_SERVER_ERROR, null));

        } catch (AuthenticationException ex) {
            log.warn("Error validating credentials: " + ex.getMessage());
            return buildResponse("No se puedo validar las credenciales, intente de nuevo", HttpStatus.UNAUTHORIZED, null);
        }
    }

    @PostMapping("sign-up")
    public ResponseEntity<HttpBaseResponse> register(@RequestBody @NotNull final RegisterUserDto register) {
        if(!userService.isNewUser(register.email(), register.phone())) {
            log.info("The user with email: " + register.email() + " already exists");
            return buildResponse("No se puedo validar las credenciales, intente de nuevo", HttpStatus.UNAUTHORIZED, null);
        }

        Optional<RoleDto> role = userService.findByName(register.rolName());
        if(role.isEmpty()) {
            return buildResponse("Role not identified", HttpStatus.BAD_GATEWAY, null);
        }

        String passwordHashed = encoder.encode(register.password());
        UserDTO userDto = userService.save(register, passwordHashed, role.get());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.get().name().name())));
        role.stream()
                .flatMap(r -> r.permissions().stream())
                .forEach(per -> new SimpleGrantedAuthority(per.name()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.email(), null, authorities);
        Optional<String> token = utils.createToken(authentication, userDto.getFullNames());

        return token.map(s ->
                        buildResponse("User login successfully", HttpStatus.OK, new TokenResponse(s, userDto.email())))
                .orElseGet(() ->
                        buildResponse("Error creating token", HttpStatus.INTERNAL_SERVER_ERROR, null));

    }

}
