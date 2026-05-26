package com.config;

import Enums.entitiesEnums.ROLE_NAME;
import com.security.middleware.JwtTokenValidator;
import com.security.service.UserDetailsServiceImpl;
import com.security.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtils utils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers("/static/**",
                            "/css/**",
                            "/img/**",
                            "/js/**",
                            "/META-INF/**",
                            "/scss/**",
                            "/vendor/**",
                            "/WEB-INF/**",
                            "/login.html",
                            "/register.html",
                            "/shared/**",
                            "/auth/**",
                            "/cache/**",
                            "/config/**",
                            "/public/**",
                            "/modules/**",
                            "/app/**",
                            "/").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/auth/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/auth/log-in").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/auth/sign-up").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/auth/log-out").permitAll(); // logout siempre accesible aunque el token haya expirado
                    http.requestMatchers(HttpMethod.GET, "/api/health").permitAll();

                    // Auth: /api/auth/me requiere JWT válido (cualquier rol)
                    http.requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated();

                    http.requestMatchers(HttpMethod.GET, "/api/cab/search").hasRole(ROLE_NAME.CLIENT.name());
                    http.requestMatchers(HttpMethod.GET, "/api/location/verify").hasRole(ROLE_NAME.CLIENT.name());
                    http.requestMatchers(HttpMethod.POST, "/api/payment").hasRole(ROLE_NAME.CLIENT.name());
                    http.requestMatchers(HttpMethod.POST, "/api/ride/start").hasRole(ROLE_NAME.CLIENT.name());
                    http.requestMatchers(HttpMethod.GET, "/api/ride/active").hasRole(ROLE_NAME.CLIENT.name());

                    http.requestMatchers(HttpMethod.POST, "/api/ride/info").hasAnyRole(ROLE_NAME.CLIENT.name(), ROLE_NAME.DRIVER.name());
                    http.requestMatchers(HttpMethod.POST, "/api/ride/status").hasAnyRole(ROLE_NAME.CLIENT.name(), ROLE_NAME.DRIVER.name());
                    http.anyRequest().denyAll();
                })
                .exceptionHandling(ex -> {
                    // Para rutas /api/** devolvemos JSON 401 (el fetch del frontend lo detecta).
                    // Para cualquier otra ruta redirigimos a la página de login.
                    ex.authenticationEntryPoint((request, response, authException) -> {
                        if (request.getRequestURI().startsWith("/api/")) {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"message\":\"Sesión no válida o expirada\",\"status_code\":\"401\"}");
                        } else {
                            response.sendRedirect("/modules/auth/login.html");
                        }
                    });
                    // Acceso denegado (usuario autenticado pero sin permisos): JSON 403
                    ex.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"message\":\"No tienes permisos para esta acción\",\"status_code\":\"403\"}");
                    });
                })
                // Sin form login: el sistema es stateless con JWT en cookie HttpOnly.
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenValidator(utils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
