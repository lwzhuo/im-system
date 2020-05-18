package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.service.ChannelService;
import com.zhuo.imsystem.http.util.CommonException;
import com.zhuo.imsystem.http.util.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

// channel的管理 偏向channel维度
@RestController
@RequestMapping("/channel")
public class ChannelController extends BaseController  {
    private static Logger logger = LoggerFactory.getLogger(ChannelController.class);

    @Autowired
    ChannelService channelService;

    // 创建频道
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseJson createChannel(@RequestBody JSONObject json) throws Exception{
        ChannelDto channelDto = json.toJavaObject(ChannelDto.class);
        channelDto = channelService.createChannel(channelDto);
        return success().setData(channelDto);
    }

    // 获取频道信息 成员列表
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public ResponseJson getChannelInfo(@RequestParam String uid,@RequestParam String channelId)throws Exception{
        ChannelDto channelDto = channelService.getInfo(channelId,uid);
        return success().setData(channelDto);
    }

    // 判断是否为群组管理员
    @RequestMapping(value = "/{channelId}/is-admin",method = RequestMethod.GET)
    public ResponseJson isAdmin(HttpServletRequest request,@PathVariable String channelId) throws Exception{
        String uid = (String)request.getAttribute("uid");
        boolean res = channelService.isAdmin(uid,channelId);
        return success().setData(res);
    }

    // 进入房间
    @RequestMapping(value = "/join",method = RequestMethod.GET)
    public ResponseJson joinChannel(@RequestParam String channelId,@RequestParam String uid) throws Exception{
        ChannelMemberDto channelMemberDto = channelService.joinGroupChannel(channelId,uid);
        channelService.sendEnterChannelMessage(channelId,uid,channelMemberDto.getChannelType());
        return success().setData(channelMemberDto);
    }

    // 批量进入房间
    @RequestMapping(value = "/join-batch",method = RequestMethod.POST)
    public ResponseJson joinChannelBatch(@RequestBody JSONObject json) throws Exception{
        String channelId = json.getString("channelId");
        List<String> uidList = (List<String>) json.get("uidList");
        List<ChannelMemberDto> res = new ArrayList<ChannelMemberDto>();
        for(String uid:uidList){
            ChannelMemberDto channelMemberDto = channelService.joinGroupChannel(channelId,uid);
            res.add(channelMemberDto);
            channelService.sendEnterChannelMessage(channelId,uid,channelMemberDto.getChannelType());
        }
        return success().setData(res);
    }

    // 退出房间/管理员移除群聊
    @RequestMapping(value = "/left",method = RequestMethod.GET)
    public ResponseJson leftChannel(HttpServletRequest request,@RequestParam String channelId,@RequestParam String uid) throws Exception{
        // 检查权限 是否为其中两种情况 1.管理员移除群聊 2.用户自己退出
        String uidFromToken = (String)request.getAttribute("uid"); // 获取token中的uid
        boolean byUser = uid.equals(uidFromToken); // 是否为用户自身
        int leftReason = ConstVar.LEFT_CHANNEL;
        if(!byUser){
            // 检查是否为管理员操作
            boolean isAdmin = channelService.isAdmin(uidFromToken,channelId);
            if(!isAdmin)
                throw new CommonException(StatusCode.ERROR_CHANNEL_LEFT_FAILED,"没有权限");
            // 检查是否是管理员自我删除
            if(uid.equals(uidFromToken))
                throw new CommonException(StatusCode.ERROR_CHANNEL_LEFT_FAILED,"没有权限");
            leftReason = ConstVar.KICK_OUT_BY_ADMIN;
        }
        boolean res = channelService.leftGroupChannel(channelId,uid);
        if(res){
            channelService.sendLeftChannelMessage(channelId,uid,ConstVar.GROUP_CHANNEL,leftReason);
            return success().setData("退出群聊成功");
        }else {
            return error("退出群聊失败");
        }
    }

    // 获取公开群组的channelid
    @RequestMapping(value = "/get-public-channel-id",method = RequestMethod.POST)
    public ResponseJson getPublicChannelId(@RequestBody JSONObject json) throws Exception{
        ChannelDto channelDto = json.toJavaObject(ChannelDto.class);
        String channelId = channelService.getChannelIdByPublicUrl(channelDto);
        return success().setData(channelId);
    }
}
