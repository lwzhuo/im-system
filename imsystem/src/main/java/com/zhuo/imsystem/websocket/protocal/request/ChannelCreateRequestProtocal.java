package com.zhuo.imsystem.websocket.protocal.request;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

// channel创建请求
public class ChannelCreateRequestProtocal extends RequestProtocal {
    private int createdChannelType; // 被创建的channel类型
    private String messageId;
    private String channelName;
    public ChannelCreateRequestProtocal(String channelId,String fromUid,int createdChannelType,String channelName,String messageId){
        this.channelId = channelId;
        this.fromUid = fromUid;
        this.createdChannelType = createdChannelType; // 描述创建的房间的channel类型
        this.channelType = ConstVar.SYSTEM_CHANNEL_QUEUE; // 创建房间消息本身是系统消息
        this.action = ProtocalMap.Channel_create_Request;
        this.messageId = messageId;
        this.ts = System.currentTimeMillis();
        this.msgType = ConstVar.TEXT_MESSAGE_TYPE;
        this.channelName = channelName;
    }

    public int getCreatedChannelType() {
        return createdChannelType;
    }

    public void setCreatedChannelType(int createdChannelType) {
        this.createdChannelType = createdChannelType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
