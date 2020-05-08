package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ShareMessageItemDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ShareMessageItemMapper {
    @Insert("insert into im_share_message_item (share_id,message_id,ctime,updatetime) values (#{shareId},#{messageId},#{ctime},#{updatetime})")
    public boolean saveShareItems(ShareMessageItemDto ShareMessageItemDto);
}
