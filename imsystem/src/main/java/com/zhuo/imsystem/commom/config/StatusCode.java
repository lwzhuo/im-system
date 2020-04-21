package com.zhuo.imsystem.commom.config;

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
    public static int ERROR_CHANNEL_QUERY_PARAM_INVALID = -301; // channel查询参数错误

    // websocket服务相关
    public static int ERROR_WEBSOCKET_PROTOCAL_FORMAT_INVALID = -400; // 协议格式错误 无法正常解码
    public static int ERROR_WEBSOCKET_CHANNEL_REGISTER_FAILED = -401; // channel注册失败

    // 权限相关
    public static int ERROR_INVALID_USER = -500;            // 非法用户
    public static int ERROR_CHANNEL_AUTH_FAILED = -501;     // 没有该channel的权限
}
