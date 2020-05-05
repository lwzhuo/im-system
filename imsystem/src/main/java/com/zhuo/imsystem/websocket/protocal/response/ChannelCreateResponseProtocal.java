package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class ChannelCreateResponseProtocal extends ResponseProtocal {
    private int createdChannelType; // 被创建的channel类型
    private String channelName;
    public ChannelCreateResponseProtocal(String channelId,String fromUid,int createdChannelType,String channelName,String msg){
        this.channelId = channelId;
        this.fromUid = fromUid;
        this.msg = msg;
        this.action = ProtocalMap.Channel_create_Response;
        this.msgType = ConstVar.TEXT_MESSAGE_TYPE;
        this.channelType = ConstVar.SYSTEM_CHANNEL_QUEUE;
        this.createdChannelType = createdChannelType;
        this.channelName = channelName;
    }

    public int getCreatedChannelType() {
        return createdChannelType;
    }

    public void setCreatedChannelType(int createdChannelType) {
        this.createdChannelType = createdChannelType;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
