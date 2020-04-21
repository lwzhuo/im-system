package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.http.service.MessageService;
import com.zhuo.imsystem.queue.producer.BlockingQueueProvider;
import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
    public void sendMessage(NewMessageRequestProtocal msg) throws Exception{
        BlockingQueueProvider.publish(msg.getMsgType(),msg.getAction(),msg.getJsonString());
    }
}
