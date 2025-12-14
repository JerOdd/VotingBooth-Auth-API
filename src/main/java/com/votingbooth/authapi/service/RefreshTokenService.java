package com.votingbooth.authapi.service;

import com.votingbooth.authapi.model.RefreshToken;
import com.votingbooth.authapi.model.User;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    public RefreshToken create(User user) {
        return new RefreshToken();
    }

    public RefreshToken verify(String rawToken) {
        return new RefreshToken();
    }

    public void rotate(RefreshToken oldToken) {
        // revoke old token
    }
}
