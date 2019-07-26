package com.mick.mchat.user.security;

import com.mick.mchat.websocket.model.WebsocketMessage;

public class AddCookieMessage extends WebsocketMessage {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public AddCookieMessage setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AddCookieMessage setValue(String value) {
        this.value = value;
        return this;
    }
}
