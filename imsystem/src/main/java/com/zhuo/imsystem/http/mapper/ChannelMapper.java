package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ChannelDto;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper
public interface ChannelMapper {
    @InsertProvider(type = ChannelSQLBulider.class, method = "insertChannel")
    public int saveChannel(ChannelDto channelDto);
    
    @Select("select channel_id,channel_name,channel_type,creator_id,attender_id from im_channel where creator_id=#{creatorId} and attender_id=#{attenderId} limit 1")
    public ChannelDto queryPrivateChannelByMemberUid(ChannelDto channelDto);
}
