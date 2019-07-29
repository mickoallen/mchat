package com.mick.mchat.user;

import com.mick.mchat.error.NotFoundException;
import com.mick.mchat.user.model.User;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepository() {
        users.add(
                new User()
                        .setUuid(UUID.randomUUID())
                        .setUsername("mick")
                        .setPassword("iscool")
        );
    }

    public List<User> getAllUsers() {
        return List.of();
    }

    public User getUserFromCredentials(String username, String password) throws NotFoundException {
        return users.stream()
                .filter(user -> username.equals(user.getUsername()) && password.equals(user.getPassword()))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
