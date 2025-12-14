package com.votingbooth.authapi.security;

import com.votingbooth.authapi.model.RefreshToken;
import com.votingbooth.authapi.model.User;
import com.votingbooth.authapi.service.JwtService;
import com.votingbooth.authapi.service.RefreshTokenService;
import com.votingbooth.authapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public OAuthLoginSuccessHandler(
            JwtService jwtService,
            RefreshTokenService refreshTokenService,
            UserService userService
    ) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2AuthenticationToken auth =
                (OAuth2AuthenticationToken) authentication;

        String provider = auth.getAuthorizedClientRegistrationId(); // google | apple
        OAuth2User oauthUser = auth.getPrincipal();

        String providerSubject = oauthUser.getAttribute("sub");
        String email = oauthUser.getAttribute("email");

        User user = userService.resolveUser(
                provider,
                providerSubject,
                email
        );

        String accessToken = jwtService.createAccessToken(user);
        RefreshToken refreshToken =
                refreshTokenService.create(user);

        // ⚠️ For web: send via HttpOnly cookies
//        response.addCookie(accessTokenCookie(accessToken));
//        response.addCookie(refreshTokenCookie(refreshToken.getToken()));

        response.sendRedirect("https://app.yourcompany.com/login/success");
    }
}
