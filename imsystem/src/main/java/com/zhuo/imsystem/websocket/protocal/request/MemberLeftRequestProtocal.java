package com.zhuo.imsystem.websocket.protocal.request;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class MemberLeftRequestProtocal extends RequestProtocal {
    private int leftReason;
    public MemberLeftRequestProtocal(String channelId,String fromUid,int leftReason){
        this.leftReason = leftReason;
        this.channelId = channelId;
        this.fromUid = fromUid;
        this.ts = System.currentTimeMillis();
        this.channelType = ConstVar.GROUP_CHANNEL;
        this.action = ProtocalMap.Member_left_channel_Request;
    }

    public int getLeftReason() {
        return leftReason;
    }

    public void setLeftReason(int leftReason) {
        this.leftReason = leftReason;
    }
}
