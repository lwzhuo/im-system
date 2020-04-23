package com.zhuo.imsystem.http.service;

import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;

import java.util.List;

public interface MessageService {
    public void sendMessage(NewMessageRequestProtocal newMessageRequestProtocal) throws Exception;
    public List getMoreMessage(String channelId, long ts, int size)throws Exception;
}
