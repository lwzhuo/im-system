package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
@Mapper
public interface ChannelMemberMapper {
    @InsertProvider(type = ChannelMemberSQLBulider.class, method = "insertChannelMember")
    public int saveChannelMember(ChannelMemberDto channelMemberDto);

    // 获取状态为在channel中的用户
    @Results(id = "channelMemberResult1", value = {
            @Result(property = "channelId", column = "channel_id"),
            @Result(property = "joinTime", column = "join_time"),
            @Result(property = "leftTime", column = "left_time"),
            @Result(property = "channelType", column = "channel_type"),
            @Result(property = "userType", column = "user_type")
    })
    @Select("select * from im_channel_member where channel_id=#{channelId} and uid=#{uid} and status=1")
    public ChannelMemberDto getInChannelMember(String channelId, String uid);

    @Results(id = "channelMemberResult2", value = {
            @Result(property = "channelId", column = "channel_id"),
            @Result(property = "joinTime", column = "join_time"),
            @Result(property = "leftTime", column = "left_time"),
            @Result(property = "channelType", column = "channel_type"),
            @Result(property = "userType", column = "user_type")
    })
    @Select("select * from im_channel_member where channel_id=#{channelId}")
    public List<ChannelMemberDto> getChannelMemberList(String channelId);

    @Results(id = "channelMemberResult3", value = {
            @Result(property = "channelId", column = "channel_id"),
            @Result(property = "joinTime", column = "join_time"),
            @Result(property = "leftTime", column = "left_time"),
            @Result(property = "channelType", column = "channel_type"),
            @Result(property = "userType", column = "user_type")
    })
    @Select("select * from im_channel_member where channel_id=#{channelId} and user_type=1")
    public ChannelMemberDto getAdminMemberInfo(String channelId);

    // 通过成员uid获取群组channelId
    @Select("select channel_id from im_channel_member where uid=#{uid} and channel_type=2 and status=1")
    public List<String> getGroupChannelIdsByMemberuid(String uid);

    // 用户退出群组
    @Update("update im_channel_member set status=2,updatetime=#{date},left_time=#{date} where channel_id=#{channelId} and uid=#{uid} and status=1")
    public boolean userLeftChannel(String channelId, String uid, Date date);
}
