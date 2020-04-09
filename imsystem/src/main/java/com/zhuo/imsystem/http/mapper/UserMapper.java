package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Select("select * from im_user where uid=#{uid}")
    public User queryUser(String uid);

    @Select("select * from im_user where username=#{username}")
    public User queryUserByName(String username);

    @Insert("insert into im_user (uid,username,salt,password,regist_time,update_time) values (#{uid},#{userName},#{salt},#{password},#{registTime},#{updateTime})")
    public boolean register(User user);
}
