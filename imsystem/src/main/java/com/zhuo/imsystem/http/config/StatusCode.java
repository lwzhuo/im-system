package com.zhuo.imsystem.http.config;

public class StatusCode {
    public static int OK = 0;
    public static int ERROR = -1;

    // 登录相关
    public static int ERROR_USERNAME_DUMPLICATE = -100;     // 用户名重复
    public static int ERROR_LOGIN_INFO_INVALID = -101;      // 登录用户名或密码错误

    // 签名 token 相关
    public static int ERROR_JSON_WEB_TOKEN_INVALID = -200;  // token不合法
    public static int ERROR_JSON_WEB_TOKEN_EXPIRE = -201;   // token失效

    // channel相关
    public static int ERROR_CHANNEL_CREATE_FAIL = -300;     // channel创建失败
}
