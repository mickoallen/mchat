package com.mick.mchat.security;

import java.util.UUID;

public class AuthenticationToken {
    private UUID userUuid;
    private long expiry;

    public UUID getUserUuid() {
        return userUuid;
    }

    public AuthenticationToken setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public long getExpiry() {
        return expiry;
    }

    public AuthenticationToken setExpiry(long expiry) {
        this.expiry = expiry;
        return this;
    }
}
