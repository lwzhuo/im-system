package com.zhuo.imsystem.mapper;

import com.zhuo.imsystem.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {

    @Select("select * from user where uid=#{id}")
    @Results(id = "uid",value= {
            @Result(property = "uid",column = "uid",javaType = String.class),
            @Result(property = "nickName",column = "nick_name",javaType = String.class),
            @Result(property = "avatar",column = "avatar",javaType = String.class)
    })
    public User queryUser(String uid);
}
