package com.mick.mchat.websocket.outbound.messages;

import com.mick.mchat.websocket.outbound.OutboundMessage;

public class LoginResponseMessage extends OutboundMessage {
    private String authenticationToken;

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public LoginResponseMessage setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
        return this;
    }

    @Override
    public String toString() {
        return "LoginResponseMessage{" +
                "authenticationToken='" + authenticationToken + '\'' +
                '}';
    }
}
