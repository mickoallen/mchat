package com.mick.mchat.user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mick.mchat.error.AuthenticationFailedException;
import com.mick.mchat.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Singleton
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    public static final String COOKIE_NAME = "mchat-authentication";

    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    @Inject
    public AuthenticationService() {
        algorithm = Algorithm.HMAC256("mick actually isn't cool");
        jwtVerifier = JWT.require(algorithm).build();
    }

    public String createToken(User user) {
        try {
            return JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userUuid", user.getUuid().toString())
                    .withClaim("expiry", Instant.now().plus(1, ChronoUnit.YEARS).toEpochMilli())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException(exception);//todo
        }
    }

    public AuthenticationToken getValidAuthenticationToken(String authenticationCookie) throws AuthenticationFailedException {
        DecodedJWT decodedJWT;

        try {
            decodedJWT = jwtVerifier.verify(authenticationCookie);
        } catch (JWTVerificationException e) {
            logger.error("JWT Verification failed", e);
            throw new AuthenticationFailedException("Failed to authenticate", e);
        }

        return new AuthenticationToken()
                .setExpiry(decodedJWT.getClaim("expiry").asLong())
                .setUserUuid(UUID.fromString(decodedJWT.getClaim("userUuid").asString()));
    }
}
