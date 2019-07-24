package com.mick.mchat.user;

public class UserMapper {
    public static UserApiModel toApiModel(User user){
        return new UserApiModel()
                .setUsername(user.getUsername())
                .setUuid(user.getUuid());
    }
}
