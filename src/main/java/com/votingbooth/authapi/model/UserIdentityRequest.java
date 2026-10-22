package com.votingbooth.authapi.model;

public record UserIdentityRequest(String provider, String providerUserId, String email) { }
