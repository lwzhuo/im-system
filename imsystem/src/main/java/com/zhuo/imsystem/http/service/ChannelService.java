package com.zhuo.imsystem.http.service;

import com.zhuo.imsystem.http.dto.ChannelDto;

public interface ChannelService {
    public ChannelDto createChannel(ChannelDto channelDto) throws Exception;
    public ChannelDto getInfo(String channelId,String uid,int channelType) throws Exception;
}
