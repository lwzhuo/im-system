package com.zhuo.imsystem.websocket.protocal.request;

public class NewMessageRequestProtocal extends RequestProtocal {
    private String channelId;
    private String messageId;

    public NewMessageRequestProtocal(){
        super();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String  messageId) {
        this.messageId = messageId;
    }
}
