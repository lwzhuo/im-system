package com.zhuo.imsystem.http.dto;

import com.zhuo.imsystem.elasticsearch.Message;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShareMessageDto {
    private String channelId;
    private String shareId;
    private String shareUid;
    private String shareUserName;
    private String summary;
    private String keyword;
    private Date ctime;
    private Date updatetime;
    private List<ShareMessageItemDto> shareMessageItems;
    private List<Message> messages;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getShareUid() {
        return shareUid;
    }

    public void setShareUid(String shareUid) {
        this.shareUid = shareUid;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public List<ShareMessageItemDto> getShareMessageItems() {
        return shareMessageItems;
    }

    public void setShareMessageItems(List<ShareMessageItemDto> shareMessageItems) {
        this.shareMessageItems = shareMessageItems;
    }

    public static String generateShareId(){
        String uid = UUID.randomUUID().toString().replace("-", "");
        return uid;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getShareUserName() {
        return shareUserName;
    }

    public void setShareUserName(String shareUserName) {
        this.shareUserName = shareUserName;
    }
}
