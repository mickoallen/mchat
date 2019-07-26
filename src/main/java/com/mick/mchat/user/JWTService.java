package com.mick.mchat.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Singleton
public class JWTService {
    private final Algorithm algorithm;

    @Inject
    public JWTService() {
        algorithm = Algorithm.HMAC256("secret");
    }

    public String createToken(User user){
        try {
            return JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userUuid",user.getUuid().toString())
                    .withClaim("expiry", Instant.now().plus(1, ChronoUnit.YEARS).toEpochMilli())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);//todo
        }
    }
}
