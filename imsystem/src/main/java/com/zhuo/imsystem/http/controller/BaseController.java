package com.zhuo.imsystem.http.controller;

import com.zhuo.imsystem.http.util.ResponseJson;

public class BaseController {
    public static ResponseJson success(){
        return new ResponseJson().success();
    }
    public static ResponseJson error(){
        return new ResponseJson().error();
    }
}
