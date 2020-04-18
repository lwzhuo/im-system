package com.zhuo.imsystem.queue.consumer;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.queue.model.queue.BlockingQueueModel;
import com.zhuo.imsystem.queue.service.ThreadPoolHolder;
import com.zhuo.imsystem.queue.service.handler.MessageHandler;

import java.util.concurrent.BlockingQueue;


public class BlockingQueueConsumer {
    /**
     * 消费数据
     * @param msgType   消息频道类型 系统 私聊 群聊
     */
    public static BlockingQueueMessage consume(int msgType){
        BlockingQueue<BlockingQueueMessage> blockingQueue = (BlockingQueue<BlockingQueueMessage>) BlockingQueueModel.getQueue(msgType);
        if(blockingQueue==null){
            System.out.println("消息类型错误:"+msgType);
            return null;
        }

        BlockingQueueMessage blockingQueueMessage = BlockingQueueModel.getMessageFromQueue(blockingQueue);
        return blockingQueueMessage;
    }

    public static void start(){
        System.out.println("消息队列模块启动");
        int messageTypes[] = new int[]{ConstVar.SYSTEM_MESSAGE_TYPE,ConstVar.PRIVATE_QUEUE,ConstVar.GROUP_QUEUE};
        for(int type : messageTypes){
            Thread thread = new Thread(() -> {
                for (;;) {
                    BlockingQueueMessage message = consume(type); // 从队列中获取数据
                    if (message != null) {
                        System.out.println("从["+type+"]队列中获取消息["+message.getAction()+"]:"+message.getMsg());
                        ThreadPoolHolder.getThreadPool().execute(() -> {
                            MessageHandler.createHandler(message).doHandler(); // 获取handler并处理数据
                        });
                    }
                }
            });
            thread.setName("Consumer_thread-" + type);
            thread.start();
        }
    }
}
