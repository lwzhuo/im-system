package com.zhuo.imsystem.http.service;

import com.zhuo.imsystem.http.model.User;

public interface UserService {
    public User queryUser(String uid);
    public User queryUserByName(String name);
    public Boolean checkUserNameExist(String name);
    public Boolean register(User user) throws Exception;
}
