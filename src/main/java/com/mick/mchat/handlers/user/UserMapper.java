package com.mick.mchat.handlers.user;

import com.mick.mchat.handlers.user.out.UserOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class UserMapper {
    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public static UserOut toOut(User entity) {
        UserOut dto = new UserOut()
                .setUuid(entity.getUuid())
                .setUsername(entity.getUsername())
                .setLastActive(entity.getLastActive());

        if (entity.getAvatarUrl() != null) {
            try {
                dto.setAvatarUrl(new URL(entity.getAvatarUrl()));
            } catch (MalformedURLException e) {
                logger.error("Error transforming avatar url for user:" + entity.getUuid(), e);
            }
        }

        return dto;
    }
}
