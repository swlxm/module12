package com.epam.automation.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String createJWT(String name, String userId, String role,
                                   String audience, String issuer, long TTLMillis, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
            .claim("role", role)
            .claim("unique_name", name)
            .claim("userid", userId)
            .setIssuer(issuer)
            .setAudience(audience)
            .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        return builder.compact();
    }

    public static String generateToken(int expireMinutes) {
        String SECRET = ConfigUtils.getToken().trim();
        //String SECRET="1ee1dbbbc70949aab06bc8172b052f79ab75a145";
        long nowMillis = (new Date()).getTime();
        Date expDate = new Date(nowMillis + expireMinutes * 60 * 1000L);
        //System.out.println(expDate);
        Map map = new HashMap();
        map.put("sub", "Tester" + System.currentTimeMillis());
        map.put("auth", "ROLE_Test");
        String jwt = Jwts.builder().setClaims(map)
            .setExpiration(expDate)
            .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        return "Bearer " + jwt;
    }

    public static void main(String[] args) {
        System.out.println(TokenUtils.generateToken(60 * 24 * 2000));
    }
}
