package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Mapper
public interface ChannelMemberMapper {
    @Insert("insert into im_channel_member (channel_id,uid,join_time,left_time,user_type,status,ctime,updatetime) values (#{channelId},#{uid},#{joinTime},#{leftTime},#{userType},#{status},#{ctime},#{updateTime})")
    public boolean saveChannelMember(ArrayList<ChannelMemberDto> channelUserList);
}
