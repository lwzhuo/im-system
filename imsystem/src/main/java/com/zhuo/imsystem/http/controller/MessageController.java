package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.service.MessageService;
import com.zhuo.imsystem.http.service.UserChannelService;
import com.zhuo.imsystem.http.util.ResponseJson;
import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController{

    @Autowired
    UserChannelService userChannelService;

    @Autowired
    MessageService messageService;

    // 拉取历史消息
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ResponseJson getMessage(@RequestParam String channelId,@RequestParam int size){
        return success();
    }

    @RequestMapping(value = "send",method = RequestMethod.POST)
    public ResponseJson sendMessage(HttpServletRequest request,@RequestBody JSONObject json)throws Exception{
        NewMessageRequestProtocal newMessageRequest = json.toJavaObject(NewMessageRequestProtocal.class);
        newMessageRequest.setJsonString(json.toJSONString());
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
        messageService.sendMessage(newMessageRequest);
        return success();
    }
}
