package com.zhuo.imsystem.http.dto;

import java.util.ArrayList;

public class ChannelDto {
    private String channelId;
    private String channelName;
    private String creatorId;
    private int channelType;
    private ArrayList<ChannelMemberDto> channelUserList;

    public ArrayList<ChannelMemberDto> getChannelUserList() {
        return channelUserList;
    }

    public void setChannelUserList(ArrayList<ChannelMemberDto> channelUserList) {
        this.channelUserList = channelUserList;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }
}
