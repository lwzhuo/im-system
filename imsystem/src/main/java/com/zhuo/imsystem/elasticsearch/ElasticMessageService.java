package com.zhuo.imsystem.elasticsearch;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ElasticMessageService {
    public void createIndex();

    public void deleteIndex(String index);

    public Page<Message> queryByChannelId(String channelId, int page, int size);

    public Page<Message> queryByMessageId(long messageId);

    public void save(Message message);

    public void saveAll(List<Message> list);
}