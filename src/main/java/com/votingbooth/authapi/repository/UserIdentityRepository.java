package com.votingbooth.authapi.repository;

import com.votingbooth.authapi.model.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserIdentityRepository
        extends JpaRepository<UserIdentity, UUID> {

    Optional<UserIdentity> findByProviderAndProviderSubject(
            String provider,
            String providerSubject
    );
}
