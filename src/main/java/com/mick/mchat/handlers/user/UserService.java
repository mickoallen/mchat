package com.mick.mchat.handlers.user;

import com.mick.mchat.error.AuthenticationFailedException;
import com.mick.mchat.error.NotFoundException;
import com.mick.mchat.security.AuthenticationService;
import com.mick.mchat.websocket.WsContextStore;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @Inject
    public UserService(
            final UserRepository userRepository,
            final AuthenticationService authenticationService
    ) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    public User getUserFromCredentials(String username, String password) {
        try {
            return userRepository.getUserFromCredentials(username, password);
        } catch (NotFoundException e) {
            throw new AuthenticationFailedException(e);
        }
    }

    public String login(String username, String password) {
        User user = getUserFromCredentials(username, password);
        return authenticationService.createToken(user);
    }

    public User getUser(UUID uuid) {
        return userRepository.getUser(uuid);
    }
}
