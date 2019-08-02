package com.mick.mchat.handlers.user;

import java.util.UUID;

public class User {
    private UUID uuid;
    private String username;
    private String password;
    private long lastActive;
    private String avatarUrl;

    public UUID getUuid() {
        return uuid;
    }

    public User setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public long getLastActive() {
        return lastActive;
    }

    public User setLastActive(long lastActive) {
        this.lastActive = lastActive;
        return this;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public User setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }
}
