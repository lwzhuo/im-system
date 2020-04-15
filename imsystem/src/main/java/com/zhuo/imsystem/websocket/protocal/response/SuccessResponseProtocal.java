package com.zhuo.imsystem.websocket.protocal.response;

public class SuccessResponseProtocal extends ResponseProtocal {
    private int code;

    public SuccessResponseProtocal(String msg,int code){
        super();
        this.setMsg(msg);
        this.code = code;
    }
}
