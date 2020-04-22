package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class EchoResponseProtocal extends ResponseProtocal {
    public EchoResponseProtocal(){
        super();
        this.setChannelType(ConstVar.SYSTEM_CHANNEL_QUEUE);
        this.setAction(ProtocalMap.Echo_Response);
    }
}
