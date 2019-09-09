package com.mick.mchat.handlers.user.out;

import com.mick.mchat.websocket.outbound.OutMessage;

import java.util.Objects;
import java.util.UUID;

public class UserOut implements OutMessage {
    private UUID uuid;
    private String username;
    private long lastActive;
    private String avatarUrl;
    private boolean isOnline = false;

    public UUID getUuid() {
        return uuid;
    }

    public UserOut setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserOut setUsername(String username) {
        this.username = username;
        return this;
    }

    public long getLastActive() {
        return lastActive;
    }

    public UserOut setLastActive(long lastActive) {
        this.lastActive = lastActive;
        return this;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public UserOut setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public UserOut setOnline(boolean online) {
        isOnline = online;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOut userDto = (UserOut) o;
        return lastActive == userDto.lastActive &&
                Objects.equals(uuid, userDto.uuid) &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(avatarUrl, userDto.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, username, lastActive, avatarUrl);
    }

    @Override
    public String toString() {
        return "UserOut{" +
                "uuid=" + uuid +
                ", username='" + username + '\'' +
                ", lastActive=" + lastActive +
                ", avatarUrl=" + avatarUrl +
                '}';
    }
}
