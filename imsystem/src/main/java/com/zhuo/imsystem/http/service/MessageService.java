package com.zhuo.imsystem.http.service;

import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;

public interface MessageService {
    public void sendMessage(NewMessageRequestProtocal newMessageRequestProtocal) throws Exception;
}
