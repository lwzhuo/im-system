package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.MemberLeftRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.MemberLeftResponseProtocal;
import com.zhuo.imsystem.websocket.util.ChannelContainer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberLeftHandler extends MessageHandler {
    private static Logger logger = LoggerFactory.getLogger(MemberLeftHandler.class);
    public MemberLeftHandler(BlockingQueueMessage message){this.message = message;}
    public void doHandler(){
        String strMsg = message.getMsg();
        MemberLeftRequestProtocal memberLeftRequestProtocal = (MemberLeftRequestProtocal) ProtocalMap.toProtocalObject(strMsg);
        String channelId = memberLeftRequestProtocal.getChannelId();
        String fromUid = memberLeftRequestProtocal.getFromUid();
        int leftReason = memberLeftRequestProtocal.getLeftReason();
        int channelType = memberLeftRequestProtocal.getChannelType();

        MemberLeftResponseProtocal memberLeftResponseProtocal = new MemberLeftResponseProtocal(channelId,fromUid,leftReason);

        if(channelType== ConstVar.GROUP_CHANNEL){
            ChannelGroup channelGroup = ChannelContainer.getChannelGroupByChannelId(channelId);
            if(channelGroup==null){
                logger.error("推送用户[{}]退出群组[{}]消息失败:群组ChannelGroup不存在",fromUid,channelId);
                return;
            }
            String res = ProtocalMap.toJSONString(memberLeftResponseProtocal);
            channelGroup.writeAndFlush(new TextWebSocketFrame(res));
            logger.info("推送用户[{}]退出群组[{}]消息 退出原因:{}",fromUid,channelId,leftReason);
        }
    }
}
