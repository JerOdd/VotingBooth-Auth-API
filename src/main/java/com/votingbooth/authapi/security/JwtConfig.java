package com.votingbooth.authapi.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfig {

    @Bean
    public JWKSet jwkSet(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        RSAKey jwk = new RSAKey.Builder(publicKey)
                .privateKey(privateKey) // optional, only needed in auth-api
                .keyID("auth-key-1")
                .build();

        return new JWKSet(jwk);
    }
}
