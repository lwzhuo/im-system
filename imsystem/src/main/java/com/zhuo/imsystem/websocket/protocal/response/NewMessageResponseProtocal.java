package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class NewMessageResponseProtocal extends ResponseProtocal {
    public NewMessageResponseProtocal(){
        super();
        this.setAction(ProtocalMap.New_message_Response);
    }
    public NewMessageResponseProtocal(String msg,String channelId,String fromUid,int channelType,int msgType){
        super();
        this.msg = msg;
        this.channelId = channelId;
        this.fromUid = fromUid;
        this.channelType = channelType;
        this.msgType = msgType;
        this.setAction(ProtocalMap.New_message_Response);
    }
}
