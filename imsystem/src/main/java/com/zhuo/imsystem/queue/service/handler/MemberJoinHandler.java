package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;

public class MemberJoinHandler extends MessageHandler {
    public MemberJoinHandler(BlockingQueueMessage message){this.message = message;}
    public void doHandler(){

    }
}
