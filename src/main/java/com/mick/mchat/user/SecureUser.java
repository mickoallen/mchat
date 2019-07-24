package com.mick.mchat.user;

public class SecureUser {
    private User user;
    private MJwt token;

    public User getUser() {
        return user;
    }

    public MJwt getToken() {
        return token;
    }
}
