package com.mick.mchat.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Singleton
public class PasswordService {
    private static final String keyFactoryInstance = "PBKDF2WithHmacSHA1";
    private final String salt;

    @Inject
    public PasswordService(@Named("password.salt") String salt) {
        this.salt = salt;
    }

    public String getPasswordHash(String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65537, 128);
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance(keyFactoryInstance);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        try {
            return new String(factory.generateSecret(spec).getEncoded());
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
