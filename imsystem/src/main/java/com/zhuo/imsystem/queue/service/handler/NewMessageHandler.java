package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.elasticsearch.ElasticMessageService;
import com.zhuo.imsystem.elasticsearch.Message;
import com.zhuo.imsystem.http.config.Const;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.utils.SpringUtils;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.NewMessageResponseProtocal;
import com.zhuo.imsystem.websocket.util.ChannelContainer;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.context.ApplicationContext;

public class NewMessageHandler extends MessageHandler {
    // 运行时注入
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private ChannelMapper channelMapper = applicationContext.getBean(ChannelMapper.class);
    private ElasticMessageService elasticMessageService = applicationContext.getBean(ElasticMessageService.class);

    public NewMessageHandler(BlockingQueueMessage message){
        this.message = message;
    }
    public void doHandler(){
        String strMsg = message.getMsg();
        NewMessageRequestProtocal newMessageRequestProtocal = (NewMessageRequestProtocal) ProtocalMap.toProtocalObject(strMsg);
        String fromUid = newMessageRequestProtocal.getFromUid();
        String channelId = newMessageRequestProtocal.getChannelId();
        int messageType = newMessageRequestProtocal.getMsgType();
        int channelType = newMessageRequestProtocal.getChannelType();
        String msg = newMessageRequestProtocal.getMsg();
        long ts = newMessageRequestProtocal.getTs();
        String messageId = newMessageRequestProtocal.getMessageId();

        // 保存消息到ES
        Message stroedMessage = new Message(ts,channelId,fromUid,messageType,channelType,msg,messageId, ConstVar.MESSAGE_STATUS_NORMAL);
        elasticMessageService.save(stroedMessage);

        // 处理消息发送
        if(channelType== Const.PRIVATE_CHANNEL){
            // 私聊
            ChannelDto channelDto = channelMapper.queryPrivateChannelByCreatorUid(channelId,fromUid);
            String toUid = channelDto.getAttenderId();
            Channel userChannel = ChannelContainer.getChannelByUserId(toUid);
            // 保存消息
            if(userChannel!=null){// 用户在线
                System.out.println("用户在线 发送消息");
                NewMessageResponseProtocal responseProtocal = new NewMessageResponseProtocal(msg,channelId,fromUid,channelType,messageType);
                String res = ProtocalMap.toJSONString(responseProtocal);// todo  改为toString()
                userChannel.writeAndFlush(new TextWebSocketFrame(res));
            }else {// 用户离线
                System.out.println("用户["+toUid+"]离线");
                //todo
            }
        }else {
            // 群聊 todo

        }
    }
}
