package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ChannelDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ChannelMapper {
    @Insert("insert into im_channel (channel_id,channel_name,creator_id,channel_type,ctime,updatetime) values (#{channelId},#{channelName},#{creatorId},#{channelType},#{registTime},#{updateTime})")
    public boolean saveChannel(ChannelDto channelDto);

}
