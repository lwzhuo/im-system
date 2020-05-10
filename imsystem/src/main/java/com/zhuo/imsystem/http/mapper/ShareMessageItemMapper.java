package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ShareMessageDto;
import com.zhuo.imsystem.http.dto.ShareMessageItemDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ShareMessageItemMapper {
    @Insert("insert into im_share_message_item (share_id,message_id,ctime,updatetime) values (#{shareId},#{messageId},#{ctime},#{updatetime})")
    public boolean saveShareItems(ShareMessageItemDto ShareMessageItemDto);

    @Results(id = "shareMessageItemRes",value = {
            @Result(property = "messageId",column = "message_id"),
    })
    @Select("select message_id from im_share_message_item where share_id=#{shareId}")
    public List<String> getMessageIdsByShareId(String shareId);
}
