package com.votingbooth.authapi.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.votingbooth.authapi.model")
@EnableJpaRepositories(basePackages = "com.votingbooth.authapi.repository")
public class JpaConfig {
}
