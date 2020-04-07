package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Select("select * from im_user where uid=#{id}")
    @Results(id = "uid",value= {
            @Result(property = "uid",column = "uid",javaType = String.class),
            @Result(property = "nickName",column = "nick_name",javaType = String.class),
            @Result(property = "avatar",column = "avatar",javaType = String.class)
    })
    public User queryUser(String uid);

    @Insert("insert into im_user (uid,name,salt,password,regist_time,update_time) values (#uid,#name,#salt,#password,#registTime,#updateTime)")
    public boolean register(User user);
}
