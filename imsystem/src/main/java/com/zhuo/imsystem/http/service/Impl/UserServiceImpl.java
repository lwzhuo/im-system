package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.http.mapper.UserMapper;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.UserService;
import com.zhuo.imsystem.http.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUser(String uid){
        return this.userMapper.queryUser(uid);
    }

    @Override
    public Boolean register(User user){
        // 处理密码
        String password = user.getPassword();
        String salt = PasswordUtil.generateSalt();
        String processedPassword = PasswordUtil.getMd5Password(password,salt);
        user.setPassword(processedPassword);
        user.setSalt(salt);

        // 生成uid
        String uid = UUID.randomUUID().toString().replace("-", "");
        user.setUid(uid);

        // 处理时间
        Date now = new Date();
        user.setRegistTime(now);
        user.setUpdateTime(now);

        Boolean res = userMapper.register(user);
        return res;
    }
}
