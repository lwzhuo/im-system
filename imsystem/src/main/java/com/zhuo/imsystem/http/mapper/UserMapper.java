package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Select("select * from im_user where uid=#{uid}")
    public User queryUser(String uid);

    @Select("select * from im_user where name=#{name}")
    public User queryUserByName(String name);

    @Insert("insert into im_user (uid,name,salt,password,regist_time,update_time) values (#{uid},#{name},#{salt},#{password},#{registTime},#{updateTime})")
    public boolean register(User user);
}
