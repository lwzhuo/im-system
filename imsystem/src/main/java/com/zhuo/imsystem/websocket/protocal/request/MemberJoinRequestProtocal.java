package com.zhuo.imsystem.websocket.protocal.request;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

// 成员加入请求
public class MemberJoinRequestProtocal extends RequestProtocal {
    private String avatarUrl;
    private String userName;
    private int userType;

    public MemberJoinRequestProtocal(String channelId,String fromUid,String avatarUrl,String userName,int userType){
        this.channelId = channelId;
        this.fromUid = fromUid;
        this.avatarUrl = avatarUrl;
        this.userName = userName;
        this.userType = userType;
        this.ts = System.currentTimeMillis();
        this.channelType = ConstVar.GROUP_CHANNEL;
        this.action = ProtocalMap.Member_join_channel_Request;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
