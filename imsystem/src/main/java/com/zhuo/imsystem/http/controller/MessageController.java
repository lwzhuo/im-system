package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.elasticsearch.Message;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.mapper.UserMapper;
import com.zhuo.imsystem.http.service.MessageService;
import com.zhuo.imsystem.http.service.UserChannelService;
import com.zhuo.imsystem.http.util.ResponseJson;
import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController{

    @Autowired
    UserChannelService userChannelService;

    @Autowired
    MessageService messageService;

    @Autowired
    UserMapper userMapper;

    // 拉取历史消息 使用base+offset方式拉取ES中的数据 后续可以改为使用ES的scroll api进行拉取
    @RequestMapping(value = "/{channelId}",method = RequestMethod.GET)
    public ResponseJson getMessage(@PathVariable String channelId,@RequestParam long maxCreateAt,@RequestParam int size)throws Exception{
        List res = messageService.getMoreMessage(channelId, maxCreateAt, size);
        return success().setData(res);
    }

    // 发送消息
    @RequestMapping(value = "send",method = RequestMethod.POST)
    public ResponseJson sendMessage(HttpServletRequest request,@RequestBody JSONObject json)throws Exception{
        NewMessageRequestProtocal newMessageRequest = json.toJavaObject(NewMessageRequestProtocal.class);
        // 校验消息中的uid是否匹配
        String uid = (String) request.getAttribute("uid");
        String fromUid = newMessageRequest.getFromUid();
        if(!uid.equals(fromUid))
            return error("非法用户", StatusCode.ERROR_INVALID_USER);

        // 校验是否有权限在该channel中发消息
        String channelId = newMessageRequest.getChannelId();
        ChannelMemberDto channelMemberDto = userChannelService.getMemberChannel(channelId,uid);
        if(channelMemberDto==null)
            return error("用户没有权限", StatusCode.ERROR_CHANNEL_AUTH_FAILED);

        // 发送消息
        long ts = System.currentTimeMillis();
        String messageId = Message.generateMessageTid();
        String fromUserName = userMapper.queryUserName(fromUid);
        int messageType = newMessageRequest.getMsgType();
        int channelType = newMessageRequest.getChannelType();
        String msg = newMessageRequest.getMsg();
        newMessageRequest.setTs(ts);
        newMessageRequest.setMessageId(messageId);
        newMessageRequest.setJsonString(JSONObject.toJSONString(newMessageRequest));
        messageService.sendMessage(newMessageRequest);

        // 返回结果响应
        HashMap res = new HashMap();
        res.put("ts",ts);
        res.put("messageId",messageId);
        res.put("fromUid",fromUid);
        res.put("fromUserName",fromUserName);
        res.put("msgType",messageType);
        res.put("channelType",channelType);
        res.put("msg",msg);
        return success().setData(res);
    }
}
