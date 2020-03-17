package com.zhuo.imsystem.service.Impl;

import com.zhuo.imsystem.mapper.UserMapper;
import com.zhuo.imsystem.model.User;
import com.zhuo.imsystem.service.UserService;
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
