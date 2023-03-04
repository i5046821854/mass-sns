package dev.be.sns.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {


    public static String getUserName(String token, String key) {
        return getClaims(token, key).get("userName", String.class);
    }
    public static boolean isExpired(String token, String key) {
        Date expiredDate = getClaims(token, key).getExpiration();
        return expiredDate.before(new Date());
    }
    private static Claims getClaims(String token, String key) {
        return Jwts.parserBuilder().setSigningKey(getKey(key)).build().parseClaimsJwt(token).getBody();
    }
    public static String generateToken(String userName, String key, long expiredTimeMs){
        Claims cliams = Jwts.claims();
        cliams.put("userName", userName);
        return Jwts.builder()
                .setClaims(cliams)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expiredTimeMs))
                .signWith( getKey(key), SignatureAlgorithm.HS256)
                .compact(); //string으로
    }

    public static Key getKey(String key)
    {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
