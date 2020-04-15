package com.zhuo.imsystem.http.controller;

import com.zhuo.imsystem.http.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

// channel和用户的管理 偏向用户维度
@RestController
@RequestMapping("/user-channel")
public class UserChannelController extends BaseController{
    @RequestMapping(value = "/{uid}",method = RequestMethod.GET)
    public ResponseJson createChannel(@PathVariable String uid, @RequestParam int limit) throws Exception{
        HashMap hashMap = new HashMap();
        hashMap.put("uid",uid);
        hashMap.put("limit",limit);
        return success().setData(hashMap);
    }
}
