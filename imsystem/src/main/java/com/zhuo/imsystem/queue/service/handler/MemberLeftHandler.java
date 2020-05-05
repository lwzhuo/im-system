package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;

public class MemberLeftHandler extends MessageHandler {
    public MemberLeftHandler(BlockingQueueMessage message){this.message = message;}
    public void doHandler(){

    }
}
