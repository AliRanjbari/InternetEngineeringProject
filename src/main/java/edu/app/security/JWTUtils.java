package edu.app.security;

import edu.app.exceptions.ForbiddenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


//import jakarta.crypto.spec.SecretKeySpec;
//import jakarta.xml.bind.DatatypeConverter;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;


public class JWTUtils {
    private static String SECRET_KEY = "baloot2023";
    private static long EXPIRE_PERIOD = 24*60*60*1000;      // one day

    private static Date expirationDate() {
        long curTime = System.currentTimeMillis();
        return new Date(curTime + EXPIRE_PERIOD);
    }

    public static String createJWT(String username) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        /*byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        SecretKeySpec signingKey = new SecretKeySpec(SECRET_KEY.getBytes(), signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder();
        builder.setIssuer(userMail);
        builder.setIssuedAt(new Date(System.currentTimeMillis()));
        builder.setExpiration(expirationDate());
        builder.signWith(signatureAlgorithm, signingKey);*/



        return Jwts.builder()
                .setIssuer(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate())
                .signWith(signatureAlgorithm, SECRET_KEY)
                .compact();
    }

    public static String verifyJWT(String jwt) {
        /*Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        if(claims.getIssuedAt() == null ||
                claims.getExpiration()== null ||
                claims.getIssuer() == null)
            throw new ForbiddenException("Authorization invalid");
        if (claims.getExpiration().getTime() < System.currentTimeMillis())
            return null;
        return claims.getIssuer();*/
        return "hello";
    }
}