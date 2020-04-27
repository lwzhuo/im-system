package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.elasticsearch.Message;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.mapper.UserMapper;
import com.zhuo.imsystem.http.model.FileMessage;
import com.zhuo.imsystem.http.service.FileService;
import com.zhuo.imsystem.http.service.MessageService;
import com.zhuo.imsystem.http.service.UserChannelService;
import com.zhuo.imsystem.http.util.ResponseJson;
import com.zhuo.imsystem.websocket.protocal.request.NewMessageRequestProtocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    FileService fileService;

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
        String msg = newMessageRequest.getMsg();
        // 校验文本长度
        if(msg.length()> ConstVar.MAX_TEXT_LENGTH){
            return error("消息长度超过限制",StatusCode.ERROR_TEXT_MESSAGE_OUT_OF_LENGTH);
        }
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

    @RequestMapping(value = "/file",method = RequestMethod.POST)
    public ResponseJson uploadFile(HttpServletRequest request,@RequestParam("file")MultipartFile file,@RequestBody JSONObject json)throws Exception{
        NewMessageRequestProtocal newMessageRequest = json.toJavaObject(NewMessageRequestProtocal.class);

        // 校验文件大小
        if(file.getSize()>ConstVar.MAX_FILE_SIZE){
            return error("文件大小超过限制",StatusCode.ERROR_FILE_MESSAGE_OUT_OF_SIZE);
        }

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

        // 获取文件信息
        String filename = file.getName();
        String newFileName = fileService.generateFileName(); // 生成新的文件名
        long size = file.getSize();
        String contentType = file.getContentType();
        FileMessage fileMessage = new FileMessage(newFileName,filename,size,contentType);
        String msg = JSONObject.toJSONString(fileMessage);
        newMessageRequest.setMsg(msg);
        // 组装、发送消息
        long ts = System.currentTimeMillis();
        String messageId = Message.generateMessageTid();
        newMessageRequest.setTs(ts);
        newMessageRequest.setMessageId(messageId);
        newMessageRequest.setJsonString(JSONObject.toJSONString(newMessageRequest));
        messageService.sendMessage(newMessageRequest);

        // 保存文件
        fileService.valid(channelId);
        byte[] data = file.getBytes();
        boolean res = fileService.save(data,newFileName,channelId);
        if(res){
            String fromUserName = userMapper.queryUserName(fromUid);
            int messageType = newMessageRequest.getMsgType();
            int channelType = newMessageRequest.getChannelType();
            HashMap resp = new HashMap();
            resp.put("ts",ts);
            resp.put("messageId",messageId);
            resp.put("fromUid",fromUid);
            resp.put("fromUserName",fromUserName);
            resp.put("msgType",messageType);
            resp.put("channelType",channelType);
            resp.put("msg",msg);
            return success().setData(resp);
        }else {
            return error();
        }
    }
}
