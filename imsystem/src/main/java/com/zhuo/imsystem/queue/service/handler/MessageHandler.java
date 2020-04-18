package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public abstract class MessageHandler {
    protected BlockingQueueMessage message;
    public static MessageHandler createHandler(BlockingQueueMessage message){
        int action = message.getAction();
        switch (action) {
            case ProtocalMap.Echo_Request:
                return new EchoHandler(message);
        }
        return null;
    }

    public abstract void doHandler();
}
