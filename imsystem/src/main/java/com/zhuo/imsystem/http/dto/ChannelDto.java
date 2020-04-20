package com.zhuo.imsystem.http.dto;
import java.util.ArrayList;
import java.util.Date;

public class ChannelDto {
    private String channelId;
    private String channelName;
    private String creatorId;
    private String attenderId;
    private int channelType;
    private Date ctime;
    private Date updateTime;
    private ArrayList<ChannelMemberDto> channelUserList;

    public ChannelDto(){

    }

    public ChannelDto(String channelId, String channelName, String creatorId, String attenderId, int channelType, Date ctime, Date updateTime) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.creatorId = creatorId;
        this.attenderId = attenderId;
        this.channelType = channelType;
        this.ctime = ctime;
        this.updateTime = updateTime;
    }

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

    public String getAttenderId() {
        return attenderId;
    }

    public void setAttenderId(String attenderId) {
        this.attenderId = attenderId;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
