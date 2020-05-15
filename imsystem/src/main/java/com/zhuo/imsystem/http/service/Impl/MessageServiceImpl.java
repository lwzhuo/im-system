package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.elasticsearch.ElasticMessageService;
import com.zhuo.imsystem.elasticsearch.Message;
import com.zhuo.imsystem.http.dto.ShareMessageDto;
import com.zhuo.imsystem.http.dto.ShareMessageItemDto;
import com.zhuo.imsystem.http.mapper.ShareMessageItemMapper;
import com.zhuo.imsystem.http.mapper.ShareMessageMapper;
import com.zhuo.imsystem.http.service.KeyWordService;
import com.zhuo.imsystem.http.service.MessageService;
import com.zhuo.imsystem.http.service.UserService;
import com.zhuo.imsystem.http.util.CommonException;
import com.zhuo.imsystem.queue.producer.BlockingQueueProvider;
import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    ElasticMessageService elasticMessageService;

    @Autowired
    KeyWordService keyWordService;

    @Autowired
    ShareMessageMapper shareMessageMapper;

    @Autowired
    ShareMessageItemMapper shareMessageItemMapper;

    @Autowired
    UserService userService;

    public void sendMessage(NewMessageRequestProtocal msg) throws Exception{
        BlockingQueueProvider.publish(msg.getChannelType(),msg.getAction(),msg.getJsonString());
    }

    public List getMoreMessage(String channelId,long ts,int size)throws Exception{
        List<Message> res = elasticMessageService.getMessageDesc(channelId, ts, size);
        return res;
    }

    // 分享聊天记录
    public ShareMessageDto shareMessage(String chnanelId,String uid,List<String> messageIdList) throws Exception{
        // 从ES中获取聊天数据
        List<Message> messageList = elasticMessageService.getBatchMessageByChannelIdAndMessageIds(chnanelId,messageIdList);
        String text = "";
        for (Message msg:messageList){
            String data = msg.getMsg();
            text+=data;//todo 优化
        }

        // 将聊天数据进行关键词提取
        String[] keywords = keyWordService.getKeyWord(text);
        String keywordsStr = String.join(";",keywords);

        // 保存数据
        Date now = new Date();
        String shareId = ShareMessageDto.generateShareId();
        ShareMessageDto shareMessageDto = new ShareMessageDto();
        shareMessageDto.setChannelId(chnanelId);
        shareMessageDto.setShareUid(uid);
        shareMessageDto.setShareId(shareId);
        shareMessageDto.setKeyword(keywordsStr);
        shareMessageDto.setCtime(now);
        shareMessageDto.setUpdatetime(now);
        boolean shareInfoRes = shareMessageMapper.saveShareInfo(shareMessageDto);
        if(!shareInfoRes) {
            throw new CommonException(StatusCode.ERROR_MESSAGE_SHARE_FAILED, "消息分享失败");
        }
        List<ShareMessageItemDto> shareMessageItems = new LinkedList<ShareMessageItemDto>();
        for(String messageid:messageIdList){
            ShareMessageItemDto shareMessageItemDto = new ShareMessageItemDto();
            shareMessageItemDto.setUpdatetime(now);
            shareMessageItemDto.setCtime(now);
            shareMessageItemDto.setMessageId(messageid);
            shareMessageItemDto.setShareId(shareId);
            shareMessageItems.add(shareMessageItemDto);
            boolean shareItemRes = shareMessageItemMapper.saveShareItems(shareMessageItemDto);
            if(!shareItemRes){
                throw new CommonException(StatusCode.ERROR_MESSAGE_SHARE_FAILED,"消息分享失败");
            }
        }
        shareMessageDto.setShareMessageItems(shareMessageItems);
        // 返回结果
        return shareMessageDto;
    }

    // 获取分享的聊天记录
    public ShareMessageDto getShareMessage(String shareId){
        ShareMessageDto shareMessageDto = shareMessageMapper.getShareData(shareId);
        String channelId = shareMessageDto.getChannelId();
        List<String> messageIds = shareMessageItemMapper.getMessageIdsByShareId(shareId);
        List<Message> res = elasticMessageService.getBatchMessageByChannelIdAndMessageIds(channelId,messageIds);
        shareMessageDto.setMessages(res);
        shareMessageDto.setShareUserName(userService.queryUser(shareMessageDto.getShareUid()).getUserName());
        return shareMessageDto;
    }

    public List queryMessageByKeyword(String channelId,String keyword){
        List res = elasticMessageService.searchMessageByKeyword(channelId,keyword);
        return res;
    }
}
