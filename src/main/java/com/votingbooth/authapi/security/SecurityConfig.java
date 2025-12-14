package com.votingbooth.authapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuthLoginSuccessHandler successHandler;
    private final OAuthLoginFailureHandler failureHandler;

    public SecurityConfig(
            OAuthLoginSuccessHandler successHandler,
            OAuthLoginFailureHandler failureHandler
    ) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ❌ No sessions
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ❌ No CSRF (stateless API)
                .csrf(AbstractHttpConfigurer::disable)

                // ❌ No form login
                .formLogin(AbstractHttpConfigurer::disable)

                // ❌ No HTTP basic
                .httpBasic(AbstractHttpConfigurer::disable)

                // ✅ Only OAuth2 login
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/auth/login")
                        )
                        .redirectionEndpoint(redir -> redir
                                .baseUri("/auth/callback/*")
                        )
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                )

                // ✅ Public endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login/**",
                                "/auth/callback/**",
                                "/.well-known/jwks.json",
                                "/actuator/health"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}


