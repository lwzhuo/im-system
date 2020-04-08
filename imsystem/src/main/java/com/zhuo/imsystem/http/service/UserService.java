package com.zhuo.imsystem.http.service;

import com.zhuo.imsystem.http.model.User;

public interface UserService {
    public User queryUser(String uid);
    public User queryUserByUserName(String userName);
    public Boolean checkUserNameExist(String name);
    public Boolean register(User user) throws Exception;
    public User login(User user) throws Exception;
}
