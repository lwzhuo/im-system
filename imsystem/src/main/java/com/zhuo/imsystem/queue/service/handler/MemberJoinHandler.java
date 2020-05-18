package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.utils.SpringUtils;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.MemberJoinRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.MemberJoinResponseProtocal;
import com.zhuo.imsystem.websocket.util.ChannelContainer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberJoinHandler extends MessageHandler {
    private static Logger logger = LoggerFactory.getLogger(MemberJoinHandler.class);
    public MemberJoinHandler(BlockingQueueMessage message){this.message = message;}

    public void doHandler(){
        String strMsg = message.getMsg();
        MemberJoinRequestProtocal memberJoinRequestProtocal = (MemberJoinRequestProtocal) ProtocalMap.toProtocalObject(strMsg);
        String channelId = memberJoinRequestProtocal.getChannelId();
        String fromUid = memberJoinRequestProtocal.getFromUid();
        String userName = memberJoinRequestProtocal.getUserName();
        String avatarUrl = memberJoinRequestProtocal.getAvatarUrl();
        int userType = memberJoinRequestProtocal.getUserType();
        int channelType = memberJoinRequestProtocal.getChannelType();

        MemberJoinResponseProtocal memberJoinResponseProtocal = new MemberJoinResponseProtocal(channelId,fromUid,avatarUrl,userName,userType);
        if(channelType== ConstVar.GROUP_CHANNEL){
            ChannelGroup channelGroup = ChannelContainer.getChannelGroupByChannelId(channelId);
            String res = ProtocalMap.toJSONString(memberJoinResponseProtocal);
            if(channelGroup==null){
                logger.error("推送用户[{}]加入群组[{}]消息失败:群组ChannelGroup不存在",userName,channelId);
                return;
            }
            channelGroup.writeAndFlush(new TextWebSocketFrame(res));
            logger.info("推送用户[{}]加入群组[{}]消息",userName,channelId);
        }
    }
}
