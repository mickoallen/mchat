package com.mick.mchat.websocket.inbound;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InMessageWrapper {
    private InMessageType type;
    private String authenticationToken;

    public InMessageType getType() {
        return type;
    }

    public InMessageWrapper setType(InMessageType type) {
        this.type = type;
        return this;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public InMessageWrapper setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
        return this;
    }
}
