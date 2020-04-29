package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.service.ChannelService;
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
}
