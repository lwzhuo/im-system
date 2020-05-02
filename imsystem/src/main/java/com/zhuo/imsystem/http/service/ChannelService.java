package com.zhuo.imsystem.http.service;

import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;

public interface ChannelService {
    public ChannelDto createChannel(ChannelDto channelDto) throws Exception;
    public ChannelDto getInfo(String channelId,String uid) throws Exception;
    public boolean isAdmin(String uid,String channelId) throws Exception;
    public ChannelMemberDto joinGroupChannel(String channelId, String uid) throws Exception;
    public boolean isGroupChannel(String channelId);
    public boolean leftGroupChannel(String channelId,String uid)throws Exception;
}
