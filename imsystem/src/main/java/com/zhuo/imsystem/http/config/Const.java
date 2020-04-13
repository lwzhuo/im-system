package com.zhuo.imsystem.http.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class Const {

//    @Value("${jwt.ttl}")
    public static int JWT_TTL = 86400000;

//    @Value("${jwt.secret}")
    public static String JWT_SECRET = "ForIMSystem";

    // 私聊
    public static int PRIVATE_CHANNEL = 1;

    // 群聊
    public static int GROUP_CHALLEL = 2;
}
