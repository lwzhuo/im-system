package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;

// 通用错误协议
public class CommonErrorResponseProtocal extends ResponseProtocal {
    public CommonErrorResponseProtocal(String msg, int errorCode){
        super();
        this.setMsg(msg);
        this.setStatusCode(errorCode);
        this.setMsgType(ConstVar.SYSTEM_MESSAGE_TYPE);
    }
}
