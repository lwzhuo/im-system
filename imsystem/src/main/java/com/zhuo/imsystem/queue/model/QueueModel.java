package com.zhuo.imsystem.queue.model;

import com.zhuo.imsystem.commom.config.ConstVar;

import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class QueueModel {
    // 系统消息队列
    private static BlockingDeque<Message> SYSTEM_QUEUE = new LinkedBlockingDeque<Message>();

    // 私聊队列
    private static BlockingDeque<Message> PRIVATE_QUEUE = new LinkedBlockingDeque<Message>();

    // 群聊队列
    private static BlockingDeque<Message> GROUP_QUEUE = new LinkedBlockingDeque<Message>();

    private static HashMap<String,BlockingDeque<Message>> QUEUE_MAP = new HashMap<String,BlockingDeque<Message>>();

    static {
        QUEUE_MAP.put(ConstVar.SYSTEM_QUEUE,SYSTEM_QUEUE);
        QUEUE_MAP.put(ConstVar.PRIVATE_QUEUE,PRIVATE_QUEUE);
        QUEUE_MAP.put(ConstVar.GROUP_QUEUE,GROUP_QUEUE);
    }

    public static BlockingDeque getQueue(String queueType){
        return QUEUE_MAP.get(queueType);
    }

    public static void putMessageToQueue(Message message,BlockingDeque<Message> queue){
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Message getMessageFromQueue(BlockingDeque<Message> queue){
        try {
            return queue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
