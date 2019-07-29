package com.mick.mchat.user;

import com.mick.mchat.error.AuthenticationFailedException;
import com.mick.mchat.error.NotFoundException;
import com.mick.mchat.user.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserService {
    private final UserRepository userRepository;

    @Inject
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserFromCredentials(String username, String password){
        try {
            return userRepository.getUserFromCredentials(username, password);
        } catch (NotFoundException e) {
            throw new AuthenticationFailedException(e);
        }
    }
}
