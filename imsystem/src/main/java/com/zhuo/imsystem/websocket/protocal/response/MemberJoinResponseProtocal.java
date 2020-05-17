package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class MemberJoinResponseProtocal extends ResponseProtocal {
    private String avatarUrl;
    private String userName;
    private int userType;

    public MemberJoinResponseProtocal(String channelId,String fromUid,String avatarUrl,String userName,int userType){
        this.channelId = channelId;
        this.fromUid = fromUid;
        this.avatarUrl = avatarUrl;
        this.userName = userName;
        this.userType = userType;
        this.ts = System.currentTimeMillis();
        this.channelType = ConstVar.SYSTEM_CHANNEL;
        this.action = ProtocalMap.Member_join_channel_Response;
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
