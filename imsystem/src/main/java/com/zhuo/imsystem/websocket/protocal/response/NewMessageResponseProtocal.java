package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class NewMessageResponseProtocal extends ResponseProtocal {
    public NewMessageResponseProtocal(){
        super();
        this.setMsgType(ConstVar.PRIVATE_MESSAGE_TYPE);
        this.setAction(ProtocalMap.New_message_Response);
    }
}
