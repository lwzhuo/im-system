package com.zhuo.imsystem.queue.service.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.elasticsearch.ElasticMessageService;
import com.zhuo.imsystem.elasticsearch.Message;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.mapper.ChannelMapper;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.UserChannelService;
import com.zhuo.imsystem.http.service.UserService;
import com.zhuo.imsystem.queue.model.message.BlockingQueueMessage;
import com.zhuo.imsystem.utils.SpringUtils;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;
import com.zhuo.imsystem.websocket.protocal.request.ChannelCreateRequestProtocal;
import com.zhuo.imsystem.websocket.protocal.response.ChannelCreateResponseProtocal;
import com.zhuo.imsystem.websocket.util.ChannelContainer;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class ChannelCreateHandler extends MessageHandler {
    private ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    private ChannelMapper channelMapper = applicationContext.getBean(ChannelMapper.class);
    private ElasticMessageService elasticMessageService = applicationContext.getBean(ElasticMessageService.class);
    private UserChannelService userChannelService = applicationContext.getBean(UserChannelService.class);
    private UserService userService = applicationContext.getBean(UserService.class);

    public ChannelCreateHandler(BlockingQueueMessage message){this.message = message;}
    public void doHandler() {
        String strMsg = message.getMsg();
        ChannelCreateRequestProtocal channelCreateRequestProtocal = (ChannelCreateRequestProtocal) ProtocalMap.toProtocalObject(strMsg);
        String fromUid = channelCreateRequestProtocal.getFromUid();
        String channelId = channelCreateRequestProtocal.getChannelId();
        int channelType = channelCreateRequestProtocal.getChannelType();
        int createdChannelType = channelCreateRequestProtocal.getCreatedChannelType();
        long ts = channelCreateRequestProtocal.getTs();
        int msgType = channelCreateRequestProtocal.getMsgType();
        String channelName = channelCreateRequestProtocal.getChannelName();

        // todo 保存消息到ES (需要考虑同一份消息 内容需要显示成来自不同用户角度 消息如何保存)
        if(createdChannelType== ConstVar.GROUP_CHANNEL){
            List<ChannelMemberDto> channelMemberDtoList = userChannelService.getChannelMemberList(channelId);
            JSONObject jsonObject = new JSONObject();
            List attenderList = new ArrayList();
            for(ChannelMemberDto item : channelMemberDtoList){
                String uid = item.getUid();
                User user = userService.queryUser(uid);
                String name = user.getUserName();
                if(item.getUserType()==ConstVar.CREATOR){
                    jsonObject.put("creator",name);
                }else {
                    attenderList.add(name);
                }
            }
            jsonObject.put("attenderList",attenderList);
            ChannelGroup channelGroup = ChannelContainer.getChannelGroupByChannelId(channelId);
            ChannelCreateResponseProtocal channelCreateResponseProtocal = new ChannelCreateResponseProtocal(channelId,fromUid,createdChannelType,channelName,jsonObject.toJSONString());
            String res = ProtocalMap.toJSONString(channelCreateResponseProtocal);
            channelGroup.writeAndFlush(new TextWebSocketFrame(res));
        }else {
            ChannelDto channelDto = channelMapper.queryPrivateChannelByCreatorUid(channelId,fromUid);
            String toUid = channelDto.getAttenderId();
            Channel toChannel = ChannelContainer.getChannelByUserId(toUid);
            String msg = "channel "+channelId+" created!";
            ChannelCreateResponseProtocal channelCreateResponseProtocal = new ChannelCreateResponseProtocal(channelId,fromUid,createdChannelType,channelName,msg);
            String res = ProtocalMap.toJSONString(channelCreateResponseProtocal);
            toChannel.writeAndFlush(new TextWebSocketFrame(res));
        }
    }
}
