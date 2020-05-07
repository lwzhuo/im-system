package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ChannelDto;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

public class ChannelSQLBulider {
    // 新增channel
    public static String insertChannel(ChannelDto channelDto){
        channelDto.setCtime(new Date());
        channelDto.setUpdateTime(new Date());
        SQL sql = new SQL().
                INSERT_INTO("im_channel")
                .INTO_COLUMNS("channel_id","channel_name","creator_id","attender_id","channel_type","ctime","updatetime","summary")
                .INTO_VALUES("#{channelId}","#{channelName}","#{creatorId}","#{attenderId}","#{channelType}","#{ctime}","#{updateTime}","#{summary}");
        return sql.toString();
    }

    // 通过成员uid查询私聊channel
    public static String selectPrivateChannelByUids(ChannelDto channelDto){
        SQL sql = new SQL()
                .SELECT("channel_id","channel_name","creator_id","attender_id","channel_type")
                .FROM("im_channel")
                .WHERE("creator_id=#{creatorId}")
                .AND()
                .WHERE("attender_id=#{attenderId}");
        return sql.toString();
    }
}
