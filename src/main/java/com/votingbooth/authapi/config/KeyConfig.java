package com.votingbooth.authapi.config;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@Configuration
public class KeyConfig {

    @Value("${jwt.private-key}")
    private String privateKeyPem;

    @Bean
    public RSAPrivateKey rsaPrivateKey() throws Exception {
        String pem = privateKeyPem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", ""); // remove newlines/spaces

        byte[] decoded = Base64.getDecoder().decode(pem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    @Bean
    public RSAKey rsaKey(RSAPrivateKey privateKey) throws Exception {
        RSAPublicKey publicKey = derivePublicKey(privateKey); // see helper below
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private RSAPublicKey derivePublicKey(RSAPrivateKey privateKey) throws Exception {
        java.security.interfaces.RSAPrivateCrtKey crtKey = (java.security.interfaces.RSAPrivateCrtKey) privateKey;
        java.security.spec.RSAPublicKeySpec publicKeySpec =
                new java.security.spec.RSAPublicKeySpec(crtKey.getModulus(), crtKey.getPublicExponent());
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
