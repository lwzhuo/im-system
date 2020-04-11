package com.zhuo.imsystem.http.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuo.imsystem.http.config.Const;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.UserService;
import com.zhuo.imsystem.http.util.JWTUtil;
import com.zhuo.imsystem.http.util.ResponseJson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    // 通过uid查询信息
    @RequestMapping(value = "/{uid}",method = RequestMethod.GET)
    public ResponseJson getUser(@PathVariable String uid){
        User user = new User();
        User res = userService.queryUser(uid);
        user.setUid(res.getUid());
        user.setUserName(res.getUserName());
        user.setAvatarUrl(res.getAvatarUrl());
        return success().setData(user);
    }

    // 获取当前用户的信息
    @RequestMapping(value = "/me",method = RequestMethod.GET)
    public ResponseJson aboutMe(@RequestHeader("X-Token") String token){
        Claims claims = JWTUtil.parseJWT(token, Const.JWT_SECRET);
        String uid = JSONObject.parseObject(claims.getSubject()).getString("uid");
        User user = new User();
        User res = userService.queryUser(uid);
        user.setUid(res.getUid());
        user.setUserName(res.getUserName());
        user.setAvatarUrl(res.getAvatarUrl());
        return success().setData(user);
    }

    // 通过用户名查找用户信息
    @RequestMapping(value = "/get-user-info-by-username",method = RequestMethod.GET)
    public ResponseJson getUserInfoByName(@RequestParam("username") String username){
        User user = new User();
        User res = userService.queryUserByUserName(username);
        user.setUid(res.getUid());
        user.setUserName(res.getUserName());
        user.setAvatarUrl(res.getAvatarUrl());
        return success().setData(user);
    }
    // 注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseJson register(@RequestBody JSONObject json) throws Exception{
        User user = json.toJavaObject(User.class);
        Boolean res = userService.register(user);
        if(res)
            return success();
        else
            return error();
    }
}
