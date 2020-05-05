package com.zhuo.imsystem.websocket.protocal.request;

import com.zhuo.imsystem.commom.config.ConstVar;
import com.zhuo.imsystem.websocket.protocal.ProtocalMap;

// 将用户绑定到群组的消息协议
public class BindToGroupRequestProtocal extends RequestProtocal {
    public BindToGroupRequestProtocal(){super();}
    public BindToGroupRequestProtocal(String uid){
        this.fromUid = uid;
        this.action = ProtocalMap.Bind_to_group_channel_Request;
        this.channelType = ConstVar.SYSTEM_CHANNEL;
    }
}
