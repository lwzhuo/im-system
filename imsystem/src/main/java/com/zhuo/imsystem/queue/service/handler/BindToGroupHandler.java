package com.zhuo.imsystem.queue.service.handler;

import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.http.mapper.ChannelMemberMapper;
import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.utils.SpringUtils;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.BindToGroupRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.BindToGroupResponseProtocal;
import com.zhuo.imsystem.websocket.util.ChannelContainer;
import com.zhuo.imsystem.websocket.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

// 将用户的channel绑定到相应的群组中(channelGroup)
public class BindToGroupHandler extends MessageHandler {
    private static Logger logger = LoggerFactory.getLogger(BindToGroupHandler.class);
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private ChannelMapper channelMapper = applicationContext.getBean(ChannelMapper.class);
    private ChannelMemberMapper channelMemberMapper = applicationContext.getBean(ChannelMemberMapper.class);

    public BindToGroupHandler(BlockingQueueMessage message){
        this.message = message;
    }

    public void doHandler(){
        // 解析消息
        String strMsg = message.getMsg();
        BindToGroupRequestProtocal bindToGroupRequestProtocal = (BindToGroupRequestProtocal) ProtocalMap.toProtocalObject(strMsg);
        String fromUid = bindToGroupRequestProtocal.getFromUid();
        // 获取和该用户相关的群组channelId
        List<String> groupChannelIdList = channelMemberMapper.getGroupChannelIdsByMemberuid(fromUid);
        // 逐个绑定到群组ChannelGroup中
        logger.info("用户["+fromUid+"]开始绑定群组");
        int total = 0;
        int sucessNum = 0;
        for(String groupChannelId:groupChannelIdList){
            boolean res = SessionUtil.bindToChannelGroup(fromUid,groupChannelId);
            if(res)
                sucessNum++;
            total++;
        }

        Channel userChannel = ChannelContainer.getChannelByUserId(fromUid);
        String res = ProtocalMap.toJSONString(new BindToGroupResponseProtocal().success("绑定群组完毕 共:"+total+" 成功:"+sucessNum));
        logger.info("用户["+fromUid+"]绑定群组完毕 共:"+total+" 成功:"+sucessNum);
        userChannel.writeAndFlush(new TextWebSocketFrame(res));
    }
}
