package com.zhuo.imsystem.http.controller;

import com.zhuo.imsystem.http.util.ResponseJson;

public class BaseController {
    public static ResponseJson success(){
        return new ResponseJson().success();
    }
    public static ResponseJson error(){
        return new ResponseJson().error();
    }
    public static ResponseJson error(String msg){
        return new ResponseJson().error(msg);
    }
    public static ResponseJson error(String msg,int statusCode){
        return new ResponseJson().error(msg,statusCode);
    }
}
