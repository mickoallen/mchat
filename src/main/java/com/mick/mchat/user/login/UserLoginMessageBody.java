package com.mick.mchat.user.login;

import com.mick.mchat.websocket.model.MessageBody;
import com.mick.mchat.websocket.model.MessageType;

public class UserLoginMessageBody extends MessageBody {
    private String username;
    private String password;

    protected UserLoginMessageBody() {
        super(MessageType.USER_LOGIN);
    }

    public String getUsername() {
        return username;
    }

    public UserLoginMessageBody setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginMessageBody setPassword(String password) {
        this.password = password;
        return this;
    }
}
