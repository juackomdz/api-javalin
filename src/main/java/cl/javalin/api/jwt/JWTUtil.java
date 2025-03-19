package cl.javalin.api.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import cl.javalin.api.model.UsuarioModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {

    private static final long EXPIRATE_DURATION = 24*60*60*1000;
    private static final String SECRET_KEY = "Y2xhdmUgc2VjcmV0YSBwYXJhIGp3dCB1c2FuZG8gamF2YWxpbg==";

    private SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UsuarioModel user){
        return Jwts.builder()
            .subject(user.getUsername())
            .issuer("Javalin")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATE_DURATION))
            .signWith(getSignKey())
            .compact();
    }

    public Claims parseClaims(String token){
        return Jwts.parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
