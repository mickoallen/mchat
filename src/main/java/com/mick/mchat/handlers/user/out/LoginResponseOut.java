package com.mick.mchat.handlers.user.out;

import com.mick.mchat.security.AuthenticationService;
import com.mick.mchat.websocket.outbound.OutMessage;

public class LoginResponseOut implements OutMessage {
    private String authenticationToken;
    private String cookieName = AuthenticationService.COOKIE_NAME;
    private long cookieExpiry = 365;

    public LoginResponseOut() {
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public LoginResponseOut setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
        return this;
    }

    public String getCookieName() {
        return cookieName;
    }

    public long getCookieExpiry() {
        return cookieExpiry;
    }

    @Override
    public String toString() {
        return "LoginResponseOut{" +
                "authenticationToken='" + authenticationToken + '\'' +
                ", cookieName='" + cookieName + '\'' +
                ", cookieExpiry=" + cookieExpiry +
                '}';
    }
}
