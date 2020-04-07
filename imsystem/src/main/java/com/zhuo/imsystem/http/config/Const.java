package com.zhuo.imsystem.http.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class Const {

//    @Value("${jwt.ttl}")
    public static int JWT_TTL = 600000;

//    @Value("${jwt.secret}")
    public static String JWT_SECRET = "ForIMSystem";
}
