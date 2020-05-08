package com.zhuo.imsystem.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

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
            "{\"range\": {\"ts\": {\"lt\": ?1}}}]}},\n")
    public List<Message> getMessageDesc(String channelId, long ts, Pageable pageable);

    // 通过mChannelId 和 messageId 批量获取聊天记录
    @Query("{\"bool\": {\"must\": [\n" +
            "    {\"term\": {\"channelId\": \"?0\"}},\n" +
            "    {\"terms\": {\"messageId\": ?1}}\n" +
            "    ]}\n" +
            "}")
    public List<Message> getMessageByChannelIdAndMessageIds(String channelId, List<String> messageIds, Pageable pageable);

}
