package com.mick.mchat.user;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserService {
    private final UserRepository userRepository;

    @Inject
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
