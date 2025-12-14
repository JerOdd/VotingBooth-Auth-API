package com.votingbooth.authapi.service;

import com.votingbooth.authapi.model.LoginResponse;
import com.votingbooth.authapi.model.RefreshToken;
import com.votingbooth.authapi.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    UserService userService;
    RefreshTokenService refreshTokenService;

    public AuthService(UserService userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    public LoginResponse loginFromOAuth(
            String provider,
            String providerSubject,
            String email
    ) {
        User user = userService.resolveUser(
                provider,
                providerSubject,
                email
        );

        return new LoginResponse();
    }

    public LoginResponse refresh(String refreshToken) {
        RefreshToken token =
                refreshTokenService.verify(refreshToken);

        refreshTokenService.rotate(token);

        return new LoginResponse();
    }
}
