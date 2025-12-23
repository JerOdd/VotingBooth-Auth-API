package com.votingbooth.authapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.UUID;


@Configuration
public class ClientConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {

        RegisteredClient iosClient =
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("votingbooth-ios")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("http://localhost:8080/login/oauth2/code/test")
                        .scope(OidcScopes.OPENID)
                        .scope("profile")
                        .scope("voting")
                        .build();

        RegisteredClient androidClient =
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("votingbooth-android")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("http://localhost:8080/login/oauth2/code/test")
                        .scope(OidcScopes.OPENID)
                        .scope("profile")
                        .scope("voting")
                        .build();



        RegisteredClient authClient =
                RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("votingbooth-auth")
                        .clientSecret(passwordEncoder.encode("secret"))
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("http://localhost:8080/login/oauth2/code/test")
                        .scope(OidcScopes.OPENID)
                        .scope("profile")
                        .scope("voting")
                        .scope("offline_access")
                        .build();

        return new InMemoryRegisteredClientRepository(
                iosClient,
                androidClient,
                authClient
        );
    }
}
