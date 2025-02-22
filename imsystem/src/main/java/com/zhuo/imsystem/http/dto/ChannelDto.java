package com.zhuo.imsystem.http.dto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChannelDto {
    private String channelId;
    private String channelName;
    private String creatorId;
    private String attenderId; // 参与者Id
    private String attenderName; // 参与者用户名
    private String attenderAvatar; // 参与者头像
    private String summary; // 简介
    private int channelType;
    private Date ctime;
    private Date updateTime;
    private List<ChannelMemberDto> channelUserList;
    private int memberCount; // 成员数量
    private String picUrl; // 图片链接
    private String publicUrl;

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

    public List<ChannelMemberDto> getChannelUserList() {
        return channelUserList;
    }

    public void setChannelUserList(List<ChannelMemberDto> channelUserList) {
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

    public String getAttenderAvatar() {
        return attenderAvatar;
    }

    public void setAttenderAvatar(String attenderAvatar) {
        this.attenderAvatar = attenderAvatar;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getAttenderName() {
        return attenderName;
    }

    public void setAttenderName(String attenderName) {
        this.attenderName = attenderName;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    public void setPublicUrl(String publicUrl) {
        this.publicUrl = publicUrl;
    }
}
