package com.ollirum.ms_profiles.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-users", url = "${ms-users.url}")
public interface UsersClient {
    @PatchMapping("/api/auth/{id}/disable")
    void disableUser(@PathVariable("id") Long id,
                     @RequestHeader("Authorization") String authHeader);
}