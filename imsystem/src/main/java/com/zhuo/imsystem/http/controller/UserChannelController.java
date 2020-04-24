package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.service.ChannelService;
import com.zhuo.imsystem.http.service.UserChannelService;
import com.zhuo.imsystem.http.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// channel和用户的管理 偏向用户维度
@RestController
@RequestMapping("/user-channel")
public class UserChannelController extends BaseController{
    @Autowired
    ChannelService channelService;

    @Autowired
    UserChannelService userChannelService;

    // 获取用户频道列表
    @RequestMapping(value = "/{uid}",method = RequestMethod.GET)
    public ResponseJson createChannel(@PathVariable String uid, @RequestParam int limit) throws Exception{
        // todo limit 限制数量
        List<ChannelDto> res = userChannelService.getUserChannelList(uid);
        return success().setData(res);
    }

}
