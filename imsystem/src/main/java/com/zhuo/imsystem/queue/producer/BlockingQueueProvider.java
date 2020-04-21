package com.zhuo.imsystem.queue.producer;

import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.queue.model.queue.BlockingQueueModel;

import java.util.concurrent.BlockingQueue;

public class BlockingQueueProvider {

    /**
     * 发布数据
     * @param msgType   消息频道类型 系统 私聊 群聊
     * @param action    消息动作 区分业务类型
     * @param msg       具体消息内容
     */
    public static void publish(int msgType,int action,String msg){
        BlockingQueue<BlockingQueueMessage> blockingQueue = (BlockingQueue<BlockingQueueMessage>)BlockingQueueModel.getQueue(msgType);
        if(blockingQueue==null){
            System.out.println("消息类型错误:"+msgType+" "+action+" "+msg);
            return;
        }
        BlockingQueueMessage blockingQueueMessage = new BlockingQueueMessage(action,msg);
        BlockingQueueModel.putMessageToQueue(blockingQueueMessage,blockingQueue);
        System.out.println("[消息入队]"+" msgType:"+msgType+" action:"+action+" msg:"+msg);
    }
}
