package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.http.mapper.UserMapper;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUser(String uid){
        return this.userMapper.queryUser(uid);
    }
}
