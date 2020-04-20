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

    @Results(id = "channelResult", value = {
            @Result(property = "channelId", column = "channel_id", id = true),
            @Result(property = "channelName", column = "channel_name"),
            @Result(property = "creatorId", column = "creator_id"),
            @Result(property = "attenderId", column = "attender_id"),
            @Result(property = "channelType", column = "channel_type")
    })
    @Select("select * from im_channel where creator_id=#{creatorId} and attender_id=#{attenderId} limit 1")
    public ChannelDto queryPrivateChannelByMemberUid(String creatorId,String attenderId);
}
