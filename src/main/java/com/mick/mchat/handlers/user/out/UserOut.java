package com.mick.mchat.handlers.user.out;

import com.mick.mchat.websocket.outbound.OutMessage;

import java.net.URL;
import java.util.Objects;
import java.util.UUID;

public class UserOut implements OutMessage {
    private UUID uuid;
    private String username;
    private long lastActive;
    private URL avatarUrl;

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

    public URL getAvatarUrl() {
        return avatarUrl;
    }

    public UserOut setAvatarUrl(URL avatarUrl) {
        this.avatarUrl = avatarUrl;
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