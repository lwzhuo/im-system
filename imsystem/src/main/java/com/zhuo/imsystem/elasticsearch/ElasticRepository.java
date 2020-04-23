package com.zhuo.imsystem.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticRepository extends ElasticsearchRepository<Message, Long> {

    // 根据channelid查询消息
    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"channelId.keyword\" : \"?\"}}}}")
    public Page<Message> getMessagebyChannelId(String channelId, Pageable pageable);

    // 根据messageId查询消息
    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"messageId.keyword\" : \"?\"}}}}")
    public Page<Message> getMessagebyMessageId(long messageId, Pageable pageable);

    // 正序获取消息 用于正向翻页查询消息
//    public Page<Message> getMessageAsc(String channelId,long ts,int size);

    // 倒序获取消息 用于向前查看历史消息
    @Query("{\"bool\": {\"must\": \n" +
            "[{\"term\": {\"channelId\": \"?0\"}},\n" +
            "{\"range\": {\"ts\": {\"lt\": ?1}}}]}},\n" +
            "\"size\": ?2,\n" +
            "\"sort\": {\"ts\": {\"order\": \"desc\"}}")
    public Page<Message> getMessageDesc(String channelId,long ts,int size,Pageable pageable);

}
