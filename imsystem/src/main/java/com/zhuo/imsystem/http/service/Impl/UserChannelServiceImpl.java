package com.zhuo.imsystem.http.service.Impl;

import com.zhuo.imsystem.http.dto.ChannelMemberDto;
import com.zhuo.imsystem.http.mapper.ChannelMemberMapper;
import com.zhuo.imsystem.http.service.UserChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userChannelService")
public class UserChannelServiceImpl implements UserChannelService {
    @Autowired
    ChannelMemberMapper channelMemberMapper;

    // 检查用户是否在channel中
    public ChannelMemberDto getMemberChannel(String channelId,String uid) throws Exception{
        ChannelMemberDto channelMemberDto = channelMemberMapper.getChannelMember(channelId,uid);
        if(channelMemberDto==null)
            return null;
        else
            return channelMemberDto;
    }
}
