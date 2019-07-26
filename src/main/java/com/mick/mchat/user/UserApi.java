package com.mick.mchat.user;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class UserApi {
    private final UserRepository userRepository;

    @Inject
    public UserApi(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserApiModel> getAllUsers(SecureUser secureUser) {
        List<User> users = userRepository.getAllUsers();

        return users.stream()
                .map(UserMapper::toApiModel)
                .collect(Collectors.toList());
    }
}
