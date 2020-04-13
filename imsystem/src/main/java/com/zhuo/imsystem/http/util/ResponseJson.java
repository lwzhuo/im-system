package com.zhuo.imsystem.http.util;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.http.config.StatusCode;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseJson extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private static final Integer SUCCESS_STATUS = StatusCode.OK;
    private static final Integer ERROR_STATUS = StatusCode.ERROR;
    private static final String SUCCESS_MSG = "OK";
    private static final String ERROR_MSG = "ERROR";
    
    public ResponseJson() {
        super();
    }

    public ResponseJson(int code) {
        super();
        setStatusCode(code);
    }

    public ResponseJson(HttpStatus status) {
        super();
        setStatusCode(status.value());
        setMsg(status.getReasonPhrase());
    }
    
    public ResponseJson success() {
        put("msg", SUCCESS_MSG);
        put("code", SUCCESS_STATUS);
        put("data",new ArrayList<Object>());
        return this;
    }
    
    public ResponseJson success(String msg) {
        put("msg", msg);
        put("code", SUCCESS_STATUS);
        put("data",new ArrayList<Object>());
        return this;
    }

    public ResponseJson error() {
        put("msg", ERROR_MSG);
        put("code", ERROR_STATUS);
        put("data",new ArrayList<Object>());
        return this;
    }

    public ResponseJson error(String msg) {
        put("msg", msg);
        put("code", ERROR_STATUS);
        put("data",new ArrayList<Object>());
        return this;
    }

    public ResponseJson error(String msg,int statusCode) {
        put("msg", msg);
        put("code", statusCode);
        put("data",new ArrayList<Object>());
        return this;
    }

    public ResponseJson addArrayData(Object obj){
        ArrayList<Object> data = (ArrayList<Object>) get("data");
        if (data == null) {
            data = new ArrayList<Object>();
            put("data", data);
        }
        data.add(obj);
        return this;
    }


    public ResponseJson setData(Object data){
        put("data", data);
        return this;
    }

    public ResponseJson setStatusCode(int statusCode) {
        put("code", statusCode);
        return this;
    }

    public ResponseJson setMsg(String msg) {
        put("msg", msg);
        return this;
    }

    public ResponseJson setValue(String key, Object val) {
        put(key, val);
        return this;
    }
    
    /**
     * 返回JSON字符串
     */
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
