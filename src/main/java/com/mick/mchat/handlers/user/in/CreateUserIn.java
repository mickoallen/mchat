package com.mick.mchat.handlers.user.in;

import com.mick.mchat.websocket.inbound.InMessage;

public class CreateUserIn implements InMessage {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public CreateUserIn setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CreateUserIn setPassword(String password) {
        this.password = password;
        return this;
    }
}
