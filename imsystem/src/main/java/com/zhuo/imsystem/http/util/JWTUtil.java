package com.zhuo.imsystem.http.util;

import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.http.config.Const;
import com.zhuo.imsystem.http.config.StatusCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {

    /**
     * 生成加密key
     *
     * @param secret
     * @return
     */
    private static SecretKey generalKey(String secret) {
        byte[] encodedKey = Base64.getDecoder().decode(secret);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     *
     * @param subject
     * @param secret
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String subject, String secret, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey(secret);
        JwtBuilder builder = Jwts.builder().setId("jwt").setIssuedAt(now).setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @param secret
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt, String secret) {
        SecretKey key = generalKey(secret);
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static String checkJWT(String jwt){
        String res = null;
        try {
            Claims claims = JWTUtil.parseJWT(jwt, Const.JWT_SECRET);
            res = claims.getSubject();
            long expirTime = claims.getExpiration().getTime();
            long nowMillis = System.currentTimeMillis();
            if(expirTime<=nowMillis){// 校验签名是否过期
                System.out.println("token 已经过期"+jwt);
                res=null;
            }
        }catch (Exception e) {
            // 解析jwt失败
            System.out.println("token 解析失败"+jwt);
            res=null;
        }
        return res;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid","123456");
        String jwt = JWTUtil.createJWT(jsonObject.toJSONString(), Const.JWT_SECRET,Const.JWT_TTL);
        System.out.println(jwt);

        Claims afterParse = JWTUtil.parseJWT(jwt,Const.JWT_SECRET);
        System.out.println(afterParse);
        System.out.println(afterParse.getSubject());
        System.out.println(afterParse.getExpiration());
        System.out.println(afterParse.getExpiration().getTime());
    }

}
