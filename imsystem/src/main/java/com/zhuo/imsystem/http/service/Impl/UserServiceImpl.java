package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.commom.config.StatusCode;
import com.zhuo.imsystem.http.mapper.UserMapper;
import com.zhuo.imsystem.http.model.User;
import com.zhuo.imsystem.http.service.UserService;
import com.zhuo.imsystem.http.util.CommonException;
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
    public User queryUserByUserName(String name){
        return this.userMapper.queryUserByName(name);
    }
    public Boolean checkUserNameExist(String name){
        User user = queryUserByUserName(name);
        if(user!=null)
            return true;
        else
            return false;
    }

    @Override
    public Boolean register(User user) throws Exception{
        if(user.getUserName()==null)
            throw new CommonException();
        Boolean usernameIsExist = checkUserNameExist(user.getUserName());
        if(usernameIsExist)
            throw new CommonException(StatusCode.ERROR_USERNAME_DUMPLICATE,"用户名重复");
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

    public User login(User user) throws Exception{
        String name = user.getUserName();
        User userFromDB = queryUserByUserName(name);
        if(userFromDB==null)
            throw new CommonException(StatusCode.ERROR_LOGIN_INFO_INVALID,"用户名或密码错误");

        String originPassword = user.getPassword();
        String salt = userFromDB.getSalt();
        String password = userFromDB.getPassword();
        boolean isValid = PasswordUtil.isValid(originPassword,password,salt);
        if(!isValid)
            throw new CommonException(StatusCode.ERROR_LOGIN_INFO_INVALID,"用户名或密码错误");

        return userFromDB;
    }

    public boolean updateUserInfo(String uid,String avatarUrl){
        return userMapper.updateAvatar(uid,avatarUrl);
    }
}
