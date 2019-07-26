package com.mick.mchat.user;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserRepository {
    public List<User> getAllUsers() {
        return List.of();
    }
}
