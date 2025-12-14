package com.votingbooth.authapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.UUID;

@Entity
@Table(
        name = "user_identities",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"provider", "providerSubject"}
        )
)
public class UserIdentity {

    @Id
    private UUID id;

    private UUID userId;

    private String provider;          // google | apple
    private String providerSubject;   // OAuth sub
}
