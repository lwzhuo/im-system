package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

// 消息处理路由
public abstract class MessageHandler {
    protected BlockingQueueMessage message;
    public static MessageHandler createHandler(BlockingQueueMessage message){
        int action = message.getAction();
        switch (action) {
            case ProtocalMap.Echo_Request:
                return new EchoHandler(message);
            case ProtocalMap.New_message_Request:
                return new NewMessageHandler(message);
            case ProtocalMap.Bind_to_group_channel_Request:
                return new BindToGroupHandler(message);
            case ProtocalMap.Channel_create_Request:
                return new ChannelCreateHandler(message);
            case ProtocalMap.Member_join_channel_Request:
                return new MemberJoinHandler(message);
            case ProtocalMap.Member_left_channel_Request:
                return new MemberLeftHandler(message);
        }
        return null;
    }

    public abstract void doHandler();
}
