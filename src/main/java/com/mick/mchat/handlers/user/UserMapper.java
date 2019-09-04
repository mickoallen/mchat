package com.mick.mchat.handlers.user;

import com.mick.mchat.handlers.user.out.UserOut;
import com.mick.mchat.jooq.model.tables.pojos.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMapper {
    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public static UserOut toOut(User entity) {
        UserOut dto = new UserOut()
                .setUuid(entity.getUuid())
                .setUsername(entity.getUsername());

        if (entity.getAvatarUrl() != null) {
            dto.setAvatarUrl(entity.getAvatarUrl());
        }

        return dto;
    }
}
