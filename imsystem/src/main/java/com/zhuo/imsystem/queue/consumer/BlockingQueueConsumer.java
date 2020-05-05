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
        int channelType[] = new int[]{ConstVar.PRIVATE_CHANNEL_QUEUE,ConstVar.GROUP_CHANNEL_QUEUE,ConstVar.SYSTEM_CHANNEL};
        for(int type : channelType){
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

        // 队列监视线程
        Thread queueMonitorThread = new Thread(() -> {
            try {
                for (;;) {
                    String str = "[Queue-monitor]";
                    for(int type:channelType){
                        int size = BlockingQueueModel.getQueue(type).size();
                        str+=(" channelType:"+type+" size:"+size);
                    }
                    System.out.println(str);
                    Thread.sleep(10000);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        queueMonitorThread.setName("Queue-monitor");
        queueMonitorThread.start();
    }
}
