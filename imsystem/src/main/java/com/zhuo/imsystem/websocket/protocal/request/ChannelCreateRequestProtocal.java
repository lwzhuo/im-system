package com.zhuo.imsystem.websocket.protocal.request;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

// channel创建请求
public class ChannelCreateRequestProtocal extends RequestProtocal {
    private int createdChannelType; // 被创建的channel类型
    public ChannelCreateRequestProtocal(String channelId,int createdChannelType){
        this.channelId = channelId;
        this.createdChannelType = createdChannelType;
        this.action = ProtocalMap.Channel_create_Request;
        this.channelType = ConstVar.SYSTEM_CHANNEL_QUEUE;
    }
}
