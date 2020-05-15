package com.zhuo.imsystem.queue.producer;

import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.queue.model.queue.BlockingQueueModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class BlockingQueueProvider {
    private static Logger logger = LoggerFactory.getLogger(BlockingQueueProvider.class);

    /**
     * 发布数据
     * @param channelType   频道类型 系统 私聊 群聊
     * @param action        消息动作 区分业务类型
     * @param msg           具体消息内容
     */
    public static void publish(int channelType,int action,String msg){
        BlockingQueue<BlockingQueueMessage> blockingQueue = (BlockingQueue<BlockingQueueMessage>)BlockingQueueModel.getQueue(channelType);
        if(blockingQueue==null){
            logger.info("消息类型错误:"+channelType+" "+action+" "+msg);
            return;
        }
        BlockingQueueMessage blockingQueueMessage = new BlockingQueueMessage(action,msg);
        BlockingQueueModel.putMessageToQueue(blockingQueueMessage,blockingQueue);
        logger.info("[消息入队]"+" channelType:"+channelType+" action:"+action+" msg:"+msg);
    }
}
