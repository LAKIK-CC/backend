package com.backend.lakik.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.lakik.constant.Security;
import com.backend.lakik.model.UserModel;

import java.util.Date;

public class JWTUtils {

    private static final Algorithm algorithm = Algorithm.HMAC512(Security.KEY.getBytes());
    
    public static String generateAccessToken(UserModel user){
        return JWT.create()
                .withClaim("sub", user.getUsername())
                .withClaim("id", user.getIdUser())
                .withClaim("role", user.getRole().getNamaRole())
                .withClaim("nama", user.getNamaLengkap())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .sign(algorithm);
    }

    public static String generateRefreshToken(UserModel user) {
        return JWT.create()
                .withClaim("sub", user.getUsername())
                .withClaim("id", user.getIdUser())
                .withClaim("role", user.getRole().getNamaRole())
                .withClaim("nama", user.getNamaLengkap())
                .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 1000))
                .sign(algorithm);
    }

    public static DecodedJWT decodeJWTToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token.replace("Bearer ", ""));

        return decodedJWT;
    }
}