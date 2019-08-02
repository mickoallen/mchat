package com.mick.mchat.handlers.user;

import com.mick.mchat.error.NotFoundException;

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
                        .setUuid(UUID.fromString("ba410d6d-671c-466e-add7-0983ba70bd79"))
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

    public User getUser(UUID uuid) {
        return users.stream()
                .filter(user -> user.getUuid().equals(uuid)).findFirst()
                .orElseThrow(() -> new NotFoundException("Cannot find user for that UUID"));
    }
}
