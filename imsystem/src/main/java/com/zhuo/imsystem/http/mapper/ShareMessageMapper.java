package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ShareMessageDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ShareMessageMapper {
    @Insert("insert into im_share_message (channel_id,share_id,share_uid,summary,keyword,ctime,updatetime) values (#{channelId},#{shareId},#{shareUid},#{summary},#{keyword},#{ctime},#{updatetime})")
    public boolean saveShareInfo(ShareMessageDto shareMessageDto);

    @Results(id = "shareMessageRes",value = {
            @Result(property = "channelId",column = "channel_id"),
            @Result(property = "shareId",column = "share_id"),
            @Result(property = "shareUid",column = "share_uid"),
            @Result(property = "summary",column = "summary"),
            @Result(property = "keyword",column = "keyword"),
            @Result(property = "ctime",column = "ctime"),
            @Result(property = "updatetime",column = "updatetime")
    })
    @Select("select * from im_share_message where share_id=#{shareId}")
    public ShareMessageDto getShareData(String shareId);
}
