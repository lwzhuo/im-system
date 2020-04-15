package com.zhuo.imsystem.websocket.protocal.response;

public class ErrorResponseProtocal extends ResponseProtocal {
    private int errorCode;

    public ErrorResponseProtocal(String msg,int errorCode){
        super();
        this.setMsg(msg);
        this.errorCode = errorCode;
    }
}
