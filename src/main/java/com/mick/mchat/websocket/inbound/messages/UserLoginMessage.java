package com.mick.mchat.websocket.inbound.messages;

import com.mick.mchat.websocket.inbound.InboundMessage;
import com.mick.mchat.websocket.inbound.InboundMessageType;

public class UserLoginMessage extends InboundMessage {
    private String username;
    private String password;

    public UserLoginMessage() {
        super(InboundMessageType.USER_LOGIN);
    }

    public String getUsername() {
        return username;
    }

    public UserLoginMessage setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginMessage setPassword(String password) {
        this.password = password;
        return this;
    }
}
