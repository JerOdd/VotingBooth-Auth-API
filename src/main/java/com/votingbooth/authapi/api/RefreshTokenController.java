package com.votingbooth.authapi.api;

import com.votingbooth.authapi.model.RefreshRequest;
import com.votingbooth.authapi.model.TokenResponse;
import com.votingbooth.authapi.model.User;
import com.votingbooth.authapi.service.JwtService;
import com.votingbooth.authapi.service.RefreshTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RefreshTokenController {

    RefreshTokenService refreshTokenService;
    JwtService jwtService;

    public RefreshTokenController(RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(
            @RequestBody RefreshRequest request
    ) {
//        RefreshToken token =
//                refreshTokenService.verify(request.refreshToken());
//
//        User user = token.getUser();
//
//        refreshTokenService.rotate(token);
        User user = new User();
        return new TokenResponse(
                jwtService.createAccessToken(user),
                "blabla"
        );
    }
}
