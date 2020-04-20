package com.zhuo.imsystem.http.dto;

import java.util.Date;

public class ChannelMemberDto {
    private String uid;
    private String channelId;
    private Date joinTime;
    private Date leftTime;
    private int channelType;
    private int userType;

    public ChannelMemberDto(){}

    public ChannelMemberDto(String uid, String channelId, Date joinTime, int channelType, int userType, int status, Date ctime, Date updateTime) {
        this.uid = uid;
        this.channelId = channelId;
        this.joinTime = joinTime;
        this.channelType = channelType;
        this.userType = userType;
        this.status = status;
        this.ctime = ctime;
        this.updateTime = updateTime;
    }

    private int status;
    private Date ctime;
    private Date updateTime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(Date leftTime) {
        this.leftTime = leftTime;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
