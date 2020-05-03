package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.service.ChannelService;
import com.zhuo.imsystem.http.util.CommonException;
import com.zhuo.imsystem.http.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

// channel的管理 偏向channel维度
@RestController
@RequestMapping("/channel")
public class ChannelController extends BaseController  {

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

//    // 获取进入群聊房间短链接
//    @RequestMapping(value = "/{channelId}/generate-link",method = RequestMethod.GET)
//    public ResponseJson getShortLink(@PathVariable String channelId){
//        return null;
//    }
//
//    // 通过短链接的id 获取房间信息
//    @RequestMapping(value = "/info/{shortId}",method = RequestMethod.GET)
//    public ResponseJson getChannelInfoByShortId(@PathVariable String shortId){
//        return null;
//    }

    // 进入房间
    @RequestMapping(value = "/join",method = RequestMethod.GET)
    public ResponseJson joinChannel(@RequestParam String channelId,@RequestParam String uid) throws Exception{
        ChannelMemberDto channelMemberDto = channelService.joinGroupChannel(channelId,uid);
        // todo 给群聊全体下发进入房间的消息
        return success().setData(channelMemberDto);
    }

    // 退出房间/管理员移除群聊
    @RequestMapping(value = "/left",method = RequestMethod.GET)
    public ResponseJson leftChannel(HttpServletRequest request,@RequestParam String channelId,@RequestParam String uid) throws Exception{
        // 检查权限 是否为其中两种情况 1.管理员移除群聊 2.用户自己退出
        String uidFromToken = (String)request.getAttribute("uid"); // 获取token中的uid
        boolean byUser = uid.equals(uidFromToken); // 是否为用户自身
        if(!byUser){
            // 检查是否为管理员操作
            boolean isAdmin = channelService.isAdmin(uidFromToken,channelId);
            if(!isAdmin)
                throw new CommonException(StatusCode.ERROR_CHANNEL_LEFT_FAILED,"没有权限");
            // 检查是否是管理员自我删除
            if(uid.equals(uidFromToken))
                throw new CommonException(StatusCode.ERROR_CHANNEL_LEFT_FAILED,"没有权限");
        }
        boolean res = channelService.leftGroupChannel(channelId,uid);
        if(res){
            // todo 给该用户下发移出房间的消息
            return success().setData("退出群聊成功");
        }else {
            return error("退出群聊失败");
        }
    }
}
