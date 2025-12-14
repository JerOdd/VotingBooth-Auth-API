package com.votingbooth.authapi.service;

import com.votingbooth.authapi.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User resolveUser(
            String provider,
            String providerSubject,
            String email
    ) {
        // 1. lookup identity
        // 2. create user if missing
        // 3. return user
        return new User();
    }
}