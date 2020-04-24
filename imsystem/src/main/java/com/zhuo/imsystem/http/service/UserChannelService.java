package com.zhuo.imsystem.http.service;

import com.zhuo.imsystem.http.dto.ChannelDto;
import com.zhuo.imsystem.http.dto.ChannelMemberDto;

import java.util.List;

public interface UserChannelService {
    public ChannelMemberDto getMemberChannel(String channelId, String uid) throws Exception;
    public List<ChannelDto> getUserChannelList(String uid) throws Exception;
}
