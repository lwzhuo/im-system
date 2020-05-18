package com.zhuo.imsystem.websocket.protocal.response;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

public class MemberLeftResponseProtocal extends ResponseProtocal {
    private int leftReason;
    public MemberLeftResponseProtocal(String channelId,String fromUid,int leftReason){
        this.leftReason = leftReason;
        this.channelId = channelId;
        this.fromUid = fromUid;
        this.ts = System.currentTimeMillis();
        this.channelType = ConstVar.GROUP_CHANNEL;
        this.action = ProtocalMap.Member_left_channel_Response;
    }

    public int getLeftReason() {
        return leftReason;
    }

    public void setLeftReason(int leftReason) {
        this.leftReason = leftReason;
    }
}
