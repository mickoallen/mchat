package com.mick.mchat.handlers.user.in;

import com.mick.mchat.websocket.inbound.InMessage;

public class UpdateUserIn implements InMessage {
    private String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public UpdateUserIn setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }
}
