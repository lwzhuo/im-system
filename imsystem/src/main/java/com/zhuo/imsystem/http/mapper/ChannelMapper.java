package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ChannelDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper
public interface ChannelMapper {
    @InsertProvider(type = ChannelSQLBulider.class, method = "insertChannel")
    public int saveChannel(ChannelDto channelDto);

    @Results(id = "ChannelResult1", value = {
            @Result(property = "channelId", column = "channel_id", id = true),
            @Result(property = "channelName", column = "channel_name"),
            @Result(property = "creatorId", column = "creator_id"),
            @Result(property = "attenderId", column = "attender_id"),
            @Result(property = "channelType", column = "channel_type"),
            @Result(property = "picUrl", column = "pic_url"),
            @Result(property = "summary", column = "summary"),
    })
    @Select("select * from im_channel where creator_id=#{creatorId} and attender_id=#{attenderId} limit 1")
    public ChannelDto queryPrivateChannelByMemberUid(String creatorId,String attenderId);

    @Results(id = "ChannelResult2", value = {
            @Result(property = "channelId", column = "channel_id", id = true),
            @Result(property = "channelName", column = "channel_name"),
            @Result(property = "creatorId", column = "creator_id"),
            @Result(property = "attenderId", column = "attender_id"),
            @Result(property = "channelType", column = "channel_type"),
            @Result(property = "picUrl", column = "pic_url"),
            @Result(property = "summary", column = "summary"),
    })
    @Select("select * from im_channel where channel_id=#{channelId}")
    public List<ChannelDto> queryChannelInfoByChannelId(String ChannelId);

    @Results(id = "ChannelResult3", value = {
            @Result(property = "channelId", column = "channel_id", id = true),
            @Result(property = "channelName", column = "channel_name"),
            @Result(property = "creatorId", column = "creator_id"),
            @Result(property = "attenderId", column = "attender_id"),
            @Result(property = "channelType", column = "channel_type"),
            @Result(property = "picUrl", column = "pic_url"),
            @Result(property = "summary", column = "summary"),
    })
    @Select("select * from im_channel where creator_id=#{creatorId} and channel_id=#{channelId} limit 1")
    public ChannelDto queryPrivateChannelByCreatorUid(String channelId,String creatorId);

    // 查询用户状态为在channel中的channelId
    @Select("select channel_id from im_channel_member where uid=#{uid} and status=1")
    public List<String> queryChannelIdsByMemberUid(String uid);

    @Select("select channel_id from im_channel_member where channel_type in(2,4)")
    public List<String> queryAllGroupChannelIds(); // 获取所有的群组channelID
}
