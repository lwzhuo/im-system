package com.zhuo.imsystem.http.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface PublicChannelDictMapper {
    @Select("select channel_id from im_public_channel_dict where public_url=#{url}")
    public String getChannelIdByOuterUrl(String url);
}
