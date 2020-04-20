package com.zhuo.imsystem.http.mapper;

import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import org.apache.ibatis.jdbc.SQL;

public class ChannelMemberSQLBulider {
    // 给channel中新增成员信息
    public static String insertChannelMember(ChannelMemberDto channelMemberDto){
        SQL sql =  new SQL()
                .INSERT_INTO("im_channel_member")
                .INTO_COLUMNS("channel_id","uid","join_time","user_type","channel_type","status","ctime","updatetime")
                .INTO_VALUES("#{channelId}","#{uid}","#{joinTime}","#{userType}","#{channelType}","#{status}","#{ctime}","#{updateTime}");
        return sql.toString();
    }
}
