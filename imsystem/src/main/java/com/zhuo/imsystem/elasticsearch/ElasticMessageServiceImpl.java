package com.zhuo.imsystem.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("elasticMessageService")
public class ElasticMessageServiceImpl implements ElasticMessageService {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private ElasticRepository elasticRepository;

    public void createIndex(){
        elasticsearchTemplate.createIndex(Message.class);
    }

    public void deleteIndex(String index){
        elasticsearchTemplate.deleteIndex(index);
    }

    public Page<Message> queryByChannelId(String channelId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return elasticRepository.getMessagebyChannelId(channelId,pageable);
    }

    public Page<Message> queryByMessageId(long messageId){
        Pageable pageable = PageRequest.of(0,10);
        return elasticRepository.getMessagebyMessageId(messageId,pageable);
    }

    public List<Message> getMessageDesc(String channelId, long ts, int size){
        Sort sort = Sort.by("ts").descending();
        Pageable pageable = PageRequest.of(0,size,sort);
        return elasticRepository.getMessageDesc(channelId,ts,pageable);
    }

    public void save(Message message){
        elasticRepository.save(message);
    }

    public void saveAll(List<Message> list){
        elasticRepository.saveAll(list);
    }

    public List<Message> getBatchMessageByChannelIdAndMessageIds(String channelId,List<String> messageList){
        Sort sort = Sort.by("ts").descending();
        Pageable pageable = PageRequest.of(0,100,sort);
        return elasticRepository.getMessageByChannelIdAndMessageIds(channelId,messageList,pageable);
    }
}
