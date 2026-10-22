package com.votingbooth.authapi.dataaccess;

import com.votingbooth.authapi.model.UserIdentityRequest;
import com.votingbooth.authapi.model.UserIdentityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userApiClient", url = "http://localhost:8084")
public interface UserApiClient {

    @PostMapping(value = "/user_identities", consumes = "application/json", produces = "application/json")
    UserIdentityResponse handleUserIdentity(@RequestBody UserIdentityRequest request);
}
