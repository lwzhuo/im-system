package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Mapper
public interface ChannelMemberMapper {
    @InsertProvider(type = ChannelMemberSQLBulider.class, method = "insertChannelMember")
    public int saveChannelMember(ChannelMemberDto channelMemberDto);
}
