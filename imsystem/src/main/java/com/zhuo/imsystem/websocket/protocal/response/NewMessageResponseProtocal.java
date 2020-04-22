package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class NewMessageResponseProtocal extends ResponseProtocal {
    public NewMessageResponseProtocal(){
        super();
        this.setChannelType(ConstVar.PRIVATE_CHANNEL_QUEUE);
        this.setAction(ProtocalMap.New_message_Response);
    }
}
