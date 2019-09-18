package com.mick.mchat.handlers.avatar;

import com.talanlabs.avatargenerator.AvatarException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AvatarHandlerTest {
    private static AvatarHandler avatarHandler;

    @BeforeAll
    public static void init() {
        avatarHandler = new AvatarHandler();
    }

    @Test
    public void testMaleAvatarGeneration() {
        String path = "/avatar/12345-male.png";
        byte[] avatarFromPath = avatarHandler.getAvatarFromPath(path);
        assertEquals(13964, avatarFromPath.length);
    }

    @Test
    public void testFemaleAvatarGeneration() {
        String path = "/avatar/12345-female.png";
        byte[] avatarFromPath = avatarHandler.getAvatarFromPath(path);
        assertEquals(14752, avatarFromPath.length);
    }

    @Test
    public void testInvalidPath() {
        String path = "avatar/asd-female.png";
        assertThrows(AvatarException.class, () -> avatarHandler.getAvatarFromPath(path));
    }
}
