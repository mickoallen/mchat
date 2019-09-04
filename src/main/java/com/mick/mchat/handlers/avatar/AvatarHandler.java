package com.mick.mchat.handlers.avatar;

import com.jhlabs.image.ShadowFilter;
import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.eightbit.EightBitAvatar;
import com.talanlabs.avatargenerator.element.ElementRegistry;
import com.talanlabs.avatargenerator.element.URLElementRegistry;
import com.talanlabs.avatargenerator.layers.others.ShadowLayer;
import io.javalin.http.Context;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.ByteArrayInputStream;
import java.util.List;

@Singleton
public class AvatarHandler {
    private final Avatar maleAvatar;
    private final Avatar femaleAvatar;

    @Inject
    public AvatarHandler() {
        ShadowLayer shadowLayer = new ShadowLayer(7, new ShadowFilter(5.0F, 3F, -3F, .75F));
        ElementRegistry maleElementRegistry = EightBitAvatar.newMaleElementRegistry();
        ((URLElementRegistry) maleElementRegistry).putElement("background", List.of());

        maleAvatar = EightBitAvatar.newMaleAvatarBuilder()
                .layers(shadowLayer)
                .elementRegistry(maleElementRegistry)
                .build();

        ElementRegistry femaleElementRegistry = EightBitAvatar.newFemaleElementRegistry();
        ((URLElementRegistry) femaleElementRegistry).putElement("background", List.of());
        femaleAvatar = EightBitAvatar.newFemaleAvatarBuilder()
                .layers(shadowLayer)
                .elementRegistry(femaleElementRegistry)
                .build();
    }

    public void getAvatar(Context context) {
        Avatar avatar;
        switch (getGenderFromPath(context.req.getPathInfo())) {
            case MALE:
                avatar = maleAvatar;
                break;
            case FEMALE:
                avatar = femaleAvatar;
                break;
            default:
                avatar = Math.random() > .5 ? maleAvatar : femaleAvatar;
                break;

        }

        context.result(
                new ByteArrayInputStream(
                        avatar.createAsPngBytes(
                                getCodeFromPath(
                                        context.req.getPathInfo()
                                )
                        )
                )
        )
                .contentType("image/png")
                .status(200);
    }

    private long getCodeFromPath(String pathInfo) {
        String genderAndCode = pathInfo.replace("/avatar/", "");
        return Long.parseLong(
                genderAndCode.substring(0, genderAndCode.indexOf("-"))
        );
    }

    private AvatarGender getGenderFromPath(String pathInfo) {
        String genderAndCode = pathInfo.replace("/avatar/", "");
        String genderString = genderAndCode.substring(genderAndCode.indexOf("-") + 1, genderAndCode.indexOf("."));

        return AvatarGender.valueOf(genderString.toUpperCase());
    }

    private enum AvatarGender {
        MALE, FEMALE, OTHER
    }
}
