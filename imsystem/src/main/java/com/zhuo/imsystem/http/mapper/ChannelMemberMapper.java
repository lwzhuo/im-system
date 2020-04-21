package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


@Component
@Mapper
public interface ChannelMemberMapper {
    @InsertProvider(type = ChannelMemberSQLBulider.class, method = "insertChannelMember")
    public int saveChannelMember(ChannelMemberDto channelMemberDto);

    @Select("select * from im_channel_member where channel_id=#{channelId} and uid=#{uid} and status=1")
    public ChannelMemberDto getChannelMember(String channelId,String uid);
}
