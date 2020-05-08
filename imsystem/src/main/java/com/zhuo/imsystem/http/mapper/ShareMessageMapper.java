package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ShareMessageDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ShareMessageMapper {
    @Insert("insert into im_share_message (channel_id,share_id,share_uid,summary,keyword,ctime,updatetime) values (#{channelId},#{shareId},#{shareUid},#{summary},#{keyword},#{ctime},#{updatetime})")
    public boolean saveShareInfo(ShareMessageDto shareMessageDto);
}
