package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class RegisterResponseProtocal extends ResponseProtocal {
    public RegisterResponseProtocal(){
        super();
        this.setMsgType(ConstVar.SYSTEM_MESSAGE_TYPE);
        this.setAction(ProtocalMap.Register_Response_Protocal);
    }
}
