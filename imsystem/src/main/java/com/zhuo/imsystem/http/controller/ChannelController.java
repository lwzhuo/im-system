package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.service.ChannelService;
import com.zhuo.imsystem.http.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// channel的管理 偏向channel维度
@RestController
@RequestMapping("/channel")
public class ChannelController extends BaseController  {

    @Autowired
    ChannelService channelService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseJson createChannel(@RequestBody JSONObject json) throws Exception{
        ChannelDto channelDto = json.toJavaObject(ChannelDto.class);
        channelDto = channelService.createChannel(channelDto);
        return success().setData(channelDto);
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public ResponseJson getChannelInfo(@RequestParam String uid,@RequestParam String channelId,@RequestParam int channelType)throws Exception{
        ChannelDto channelDto = channelService.getInfo(channelId,uid,channelType);
        return success().setData(channelDto);
    }
}
