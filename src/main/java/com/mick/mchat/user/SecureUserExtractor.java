package com.mick.mchat.user;

import io.javalin.http.Context;

public class SecureUserExtractor {
    private final UserRepository userRepository;

    public SecureUserExtractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SecureUser getSecureUser(Context context){
        return new SecureUser();
    }

    public SecureUser getSecureUser(String mchatToken) {
        return null;
    }
}
