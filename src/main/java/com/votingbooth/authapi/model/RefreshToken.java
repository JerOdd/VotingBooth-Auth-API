package com.votingbooth.authapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    private UUID id;

    private UUID userId;
    private String tokenHash;

    private Instant expiresAt;
    private boolean revoked;
}
