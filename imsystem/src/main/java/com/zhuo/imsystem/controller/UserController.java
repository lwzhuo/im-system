package com.zhuo.imsystem.controller;

import com.zhuo.imsystem.model.User;
import com.zhuo.imsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/{uid}",method = RequestMethod.GET)
    public User getUser(@PathVariable String uid){
        return userService.queryUser(uid);
    }
}
