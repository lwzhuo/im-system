package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.http.config.Const;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.NewMessageResponseProtocal;
import com.zhuo.imsystem.websocket.util.ChannelContainer;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class NewMessageHandler extends MessageHandler {
    @Autowired
    ChannelMapper channelMapper;

    public NewMessageHandler(BlockingQueueMessage message){
        this.message = message;
    }
    public void doHandler(){
        String strMsg = message.getMsg();
        NewMessageRequestProtocal newMessageRequestProtocal = (NewMessageRequestProtocal) ProtocalMap.toProtocalObject(strMsg);
        String fromUid = newMessageRequestProtocal.getFromUid();
        String channelId = newMessageRequestProtocal.getChannelId();
        int messageType = newMessageRequestProtocal.getMsgType();
        if(messageType== Const.PRIVATE_CHANNEL){
            // 私聊
            ChannelDto channelDto = channelMapper.queryPrivateChannelByCreatorUid(channelId,fromUid);
            String toUid = channelDto.getAttenderId();
            Channel userChannel = ChannelContainer.getChannelByUserId(toUid);
            if(userChannel!=null){// 用户在线
                System.out.println("用户在线 发送消息");
                String msg = newMessageRequestProtocal.getMsg();
                NewMessageResponseProtocal responseProtocal = new NewMessageResponseProtocal();
                responseProtocal.setFromUid(fromUid);
                String res = ProtocalMap.toJSONString(responseProtocal.success(msg));// todo  改为toString()
                userChannel.writeAndFlush(new TextWebSocketFrame(res));
            }else {// 用户离线
                System.out.println("用户离线");
                //todo
            }
        }else {
            // 群聊 todo

        }
    }
}
