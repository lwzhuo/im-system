package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.UserService;
import com.zhuo.imsystem.http.util.ResponseJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/{uid}",method = RequestMethod.GET)
    public ResponseJson getUser(@PathVariable String uid){
        return success().setData(userService.queryUser(uid));
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseJson register(@RequestBody JSONObject json){
        User user = json.toJavaObject(User.class);
        Boolean res = userService.register(user);
        if(res)
            return success().setData(json);
        else
            return error();
    }
}
