package com.mick.mchat.user.security;

import com.mick.mchat.websocket.model.MessageBody;
import com.mick.mchat.websocket.model.MessageType;

public class AddCookieMessage extends MessageBody {
    private String name;
    private String value;

    public AddCookieMessage() {
        super(MessageType.ADD_COOKIE);
    }

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
