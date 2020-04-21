package com.zhuo.imsystem.websocket.protocal.request;

public class NewMessageRequestProtocal extends RequestProtocal {
    private String channelId;

    public NewMessageRequestProtocal(){
        super();
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
