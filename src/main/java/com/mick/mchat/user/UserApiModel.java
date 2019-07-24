package com.mick.mchat.user;

import java.util.UUID;

public class UserApiModel {
    private UUID uuid;
    private String username;

    public UUID getUuid() {
        return uuid;
    }

    public UserApiModel setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserApiModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
