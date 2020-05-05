package com.zhuo.imsystem.queue.model.queue;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueModel {
    // 系统消息队列
    private static BlockingQueue<BlockingQueueMessage> SYSTEM_QUEUE = new LinkedBlockingQueue<BlockingQueueMessage>();

    // 私聊队列
    private static BlockingQueue<BlockingQueueMessage> PRIVATE_QUEUE = new LinkedBlockingQueue<BlockingQueueMessage>();

    // 群聊队列
    private static BlockingQueue<BlockingQueueMessage> GROUP_QUEUE = new LinkedBlockingQueue<BlockingQueueMessage>();

    // 公开群组队列
    private static BlockingQueue<BlockingQueueMessage> PUBLIC_GROUP_QUEUE = new LinkedBlockingQueue<BlockingQueueMessage>();

    private static HashMap<Integer,BlockingQueue<BlockingQueueMessage>> QUEUE_MAP = new HashMap<Integer, BlockingQueue<BlockingQueueMessage>>();

    static {
        QUEUE_MAP.put(ConstVar.SYSTEM_CHANNEL_QUEUE,SYSTEM_QUEUE);
        QUEUE_MAP.put(ConstVar.PRIVATE_CHANNEL_QUEUE,PRIVATE_QUEUE);
        QUEUE_MAP.put(ConstVar.GROUP_CHANNEL_QUEUE,GROUP_QUEUE);
        QUEUE_MAP.put(ConstVar.PUBLIC_CHANNEL_QUEUE,PUBLIC_GROUP_QUEUE);
    }

    public static BlockingQueue getQueue(int channelType){
        return QUEUE_MAP.get(channelType);
    }

    public static void putMessageToQueue(BlockingQueueMessage blockingQueueMessage, BlockingQueue<BlockingQueueMessage> queue){
        try {
            queue.put(blockingQueueMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static BlockingQueueMessage getMessageFromQueue(BlockingQueue<BlockingQueueMessage> queue){
        try {
            return queue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
