package com.zhuo.imsystem.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticRepository extends ElasticsearchRepository<Message, Long> {
    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"channelId.keyword\" : \"?\"}}}}")
    public Page<Message> getMessagebyChannelId(String channelId, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"messageId.keyword\" : \"?\"}}}}")
    public Page<Message> getMessagebyMessageId(long messageId, Pageable pageable);


}
