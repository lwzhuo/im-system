package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.service.ChannelService;
import com.zhuo.imsystem.http.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
