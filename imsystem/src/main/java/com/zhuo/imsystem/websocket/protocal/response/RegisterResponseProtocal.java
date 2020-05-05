package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class RegisterResponseProtocal extends ResponseProtocal {
    public RegisterResponseProtocal(){
        super();
        this.setChannelType(ConstVar.SYSTEM_CHANNEL);
        this.setAction(ProtocalMap.Register_Response);
    }
}
