package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.elasticsearch.ElasticMessageService;
import com.zhuo.imsystem.elasticsearch.Message;
import com.zhuo.imsystem.http.service.MessageService;
import com.zhuo.imsystem.queue.producer.BlockingQueueProvider;
import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.NewMessageResponseProtocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    ElasticMessageService elasticMessageService;
    public void sendMessage(NewMessageRequestProtocal msg) throws Exception{
        BlockingQueueProvider.publish(msg.getChannelType(),msg.getAction(),msg.getJsonString());
    }

    public List getMoreMessage(String channelId,long ts,int size)throws Exception{
        List<Message> res = elasticMessageService.getMessageDesc(channelId, ts, size);
        return res;
    }
}
