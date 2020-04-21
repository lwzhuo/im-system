package com.zhuo.imsystem.http.service;

import com.zhuo.imsystem.http.dto.ChannelMemberDto;

public interface UserChannelService {
    public ChannelMemberDto getMemberChannel(String channelId, String uid) throws Exception;
}
