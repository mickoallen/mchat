package com.mick.mchat.handlers.avatar;

import com.jhlabs.image.ShadowFilter;
import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.AvatarException;
import com.talanlabs.avatargenerator.eightbit.EightBitAvatar;
import com.talanlabs.avatargenerator.element.URLElementRegistry;
import com.talanlabs.avatargenerator.layers.others.ShadowLayer;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Using the {@link Avatar} library this generates random avatar PNGs based on a given seed.
 */
@Singleton
public class AvatarHandler {
    private static final Logger logger = LoggerFactory.getLogger(AvatarHandler.class);

    private static final String BACKGROUND_ELEMENT_KEY = "background";
    private static final String PNG_CONTENT_TYPE = "image/png";
    private static final String AVATAR_PATH = "/avatar/";

    private final Avatar maleAvatar;
    private final Avatar femaleAvatar;

    @Inject
    public AvatarHandler() {
        /**
         * Add a shadow cos it looked funny against pure white background.
         */
        ShadowLayer shadowLayer = new ShadowLayer(7, new ShadowFilter(5.0F, 3F, -3F, .75F));

        maleAvatar = EightBitAvatar.newMaleAvatarBuilder()
                .layers(shadowLayer)
                .elementRegistry(removeBackgroundLayer((URLElementRegistry) EightBitAvatar.newMaleElementRegistry()))
                .build();

        femaleAvatar = EightBitAvatar.newFemaleAvatarBuilder()
                .layers(shadowLayer)
                .elementRegistry(removeBackgroundLayer((URLElementRegistry) EightBitAvatar.newFemaleElementRegistry()))
                .build();
    }

    public void getAvatar(Context context) {
        logger.debug("Generating avatar for request {}", context.req.getPathInfo());
        context.result(new ByteArrayInputStream(getAvatarFromPath(context.req.getPathInfo())))
                .contentType(PNG_CONTENT_TYPE)
                .status(200);
    }

    public byte[] getAvatarFromPath(String path) {
        Avatar avatarGenerator;
        switch (getGenderFromPath(path)) {
            case MALE:
                avatarGenerator = maleAvatar;
                break;
            case FEMALE:
                avatarGenerator = femaleAvatar;
                break;
            default:
                avatarGenerator = Math.random() > .5 ? maleAvatar : femaleAvatar;
                break;
        }

        return avatarGenerator.createAsPngBytes(getCodeFromPath(path));
    }

    private long getCodeFromPath(String pathInfo) {
        try {
            String genderAndCode = pathInfo.replace(AVATAR_PATH, "");
            return Long.parseLong(
                    genderAndCode.substring(0, genderAndCode.indexOf("-"))
            );
        }catch (Exception e){
            logger.error("Invalid code in path: {}", pathInfo, e);
            throw new AvatarException("Invalid code in path " + pathInfo, e);
        }
    }

    private AvatarGender getGenderFromPath(String pathInfo) {
        try {
            String genderAndCode = pathInfo.replace(AVATAR_PATH, "");
            String genderString = genderAndCode.substring(genderAndCode.indexOf("-") + 1, genderAndCode.indexOf("."));

            return AvatarGender.valueOf(genderString.toUpperCase());
        }catch (Exception e){
            logger.error("Invalid gender in path: {}", pathInfo, e);
            throw new AvatarException("Invalid gender in path " + pathInfo, e);
        }
    }

    private URLElementRegistry removeBackgroundLayer(URLElementRegistry urlElementRegistry) {
        urlElementRegistry.putElement(BACKGROUND_ELEMENT_KEY, List.of());
        return urlElementRegistry;
    }

    private enum AvatarGender {
        MALE, FEMALE, OTHER
    }
}
