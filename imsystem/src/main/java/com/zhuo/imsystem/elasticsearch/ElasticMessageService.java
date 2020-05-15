package com.zhuo.imsystem.elasticsearch;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ElasticMessageService {
    public void createIndex();

    public void deleteIndex(String index);

    public Page<Message> queryByChannelId(String channelId, int page, int size);

    public Page<Message> queryByMessageId(long messageId);

    public List<Message> getMessageDesc(String channelId,long ts,int size);

    public void save(Message message);

    public void saveAll(List<Message> list);

    public List<Message> getBatchMessageByChannelIdAndMessageIds(String channelId,List<String> messageList);

    public List<Message> searchMessageByKeyword(String channelId,String keyword);
}