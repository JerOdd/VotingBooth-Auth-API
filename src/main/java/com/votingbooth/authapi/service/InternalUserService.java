package com.votingbooth.authapi.service;

import com.votingbooth.authapi.dataaccess.UserApiClient;
import com.votingbooth.authapi.model.UserIdentityRequest;
import com.votingbooth.authapi.model.UserIdentityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class InternalUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    @Autowired
    private UserApiClient userApiClient;

    private final OAuth2UserService<OidcUserRequest, OidcUser> delegate =
            new OidcUserService();

    @Override
    public OidcUser loadUser(OidcUserRequest request) throws OAuth2AuthenticationException {
        OidcUser oidcUser = delegate.loadUser(request);

        String provider = request.getClientRegistration().getRegistrationId();
        String providerUserId = oidcUser.getSubject();
        String email = oidcUser.getEmail();

        UserIdentityRequest userIdentityRequest = new UserIdentityRequest(provider, providerUserId, email);
        UserIdentityResponse userIdentityResponse = userApiClient.handleUserIdentity(userIdentityRequest);


        return new DefaultOidcUser(
                oidcUser.getAuthorities(),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo(),
                "sub"
        ) {
            @Override
            public String getName() {
                return userIdentityResponse.userId();
            }
        };
    }
}
